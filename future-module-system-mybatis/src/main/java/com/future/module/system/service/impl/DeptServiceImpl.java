package com.future.module.system.service.impl;

import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.exception.ServiceException;
import com.future.framework.common.utils.CollUtils;
import com.future.module.system.constants.enums.DeptId;
import com.future.module.system.dao.DeptMapper;
import com.future.module.system.domain.convert.DeptConvert;
import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.query.dept.DeptCreateQuery;
import com.future.module.system.domain.query.dept.DeptListQuery;
import com.future.module.system.domain.query.dept.DeptUpdateQuery;
import com.future.module.system.service.DeptService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.future.module.system.constants.enums.SystemErrorCode.*;

/**
 * 部门 Service 实现类
 *
 * @author JonZhang
 */
@Service
@Slf4j
public class DeptServiceImpl implements DeptService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    @Resource
    private DeptMapper deptMapper;

    /**
     * 部门缓存
     * key：部门编号 {@link Department#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @SuppressWarnings("FieldCanBeLocal")
    private volatile Map<Long, Department> deptCache;

    /**
     * 父部门缓存
     * key：部门编号 {@link Department#getParentId()}
     * value: 直接子部门列表
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    private volatile Multimap<Long, Department> parentDeptCache;

    /**
     * 缓存部门的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private volatile Date maxUpdateTime;

    @Resource
    @Lazy // 注入自己，所以延迟加载
    private DeptService self;

    @Override
    public void initLocalCache() {
        // 获取部门列表，如果有更新
        List<Department> deptList = loadDeptIfUpdate(maxUpdateTime);
        if (CollUtils.isEmpty(deptList)) {
            return;
        }
        // 构建缓存
        ImmutableMap.Builder<Long, Department> builder = ImmutableMap.builder();
        ImmutableMultimap.Builder<Long, Department> parentBuilder = ImmutableMultimap.builder();
        deptList.forEach(sysRoleDO -> {
            builder.put(sysRoleDO.getId(), sysRoleDO);
            parentBuilder.put(sysRoleDO.getParentId(), sysRoleDO);
        });
        // 设置缓存
        deptCache = builder.build();
        parentDeptCache = parentBuilder.build();

        maxUpdateTime = CollUtils.getMaxValue(deptList, Department::getUpdateTime);
        log.info("[initLocalCache][初始化 Dept 数量为 {}]", deptList.size());
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        self.initLocalCache();
    }

    @Override
    public Long createDept(DeptCreateQuery query) {
        // 校验正确性
        if (query.getParentId() == null) {
            query.setParentId(DeptId.ROOT.getId());
        }
        checkCreateOrUpdate(null, query.getParentId(), query.getName());
        // 插入部门
        Department dept = DeptConvert.INSTANCE.convert(query);
        deptMapper.insert(dept);
        return dept.getId();
    }

    @Override
    public void updateDept(DeptUpdateQuery query) {
        // 校验正确性
        if (query.getParentId() == null) {
            query.setParentId(DeptId.ROOT.getId());
        }
        checkCreateOrUpdate(query.getId(), query.getParentId(), query.getName());
        // 更新部门
        Department updateObj = DeptConvert.INSTANCE.convert(query);
        deptMapper.updateById(updateObj);
    }

    @Override
    public void deleteDept(Long id) {
        // 校验是否存在
        checkDeptExists(id);
        // 校验是否有子部门
        if (deptMapper.selectCountByParentId(id) > 0) {
            throw new ServiceException(DEPT_EXITS_CHILDREN);
        }
        // 删除部门
        deptMapper.deleteById(id);
    }

    @Override
    public List<Department> getSimpleDepts(DeptListQuery query) {
        return deptMapper.selectList(query);
    }

    @Override
    public List<Department> getDeptsByParentIdFromCache(Long parentId, boolean recursive) {
        if (parentId == null) {
            return Collections.emptyList();
        }
        List<Department> result = new ArrayList<>(); // TODO 待优化，新增缓存，避免每次遍历的计算
        // 递归，简单粗暴
        this.getDeptsByParentIdFromCache(result, parentId,
            recursive ? Integer.MAX_VALUE : 1, // 如果递归获取，则无限；否则，只递归 1 次
            parentDeptCache);
        return result;
    }

    @Override
    public List<Department> getDepts(Collection<Long> ids) {
        return deptMapper.selectBatchIds(ids);
    }

    @Override
    public Department getDept(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public void validDepts(Collection<Long> ids) {
        if (CollUtils.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        List<Department> depts = deptMapper.selectBatchIds(ids);
        Map<Long, Department> deptMap =
            depts.stream().collect(Collectors.toMap(Department::getId, i -> i));
        // 校验
        ids.forEach(id -> {
            Department dept = deptMap.get(id);
            if (dept == null) {
                throw new ServiceException(DEPT_NOT_FOUND);
            }
            if (!CommonStatus.VALID.getValue().equals(dept.getStatus())) {
                throw new ServiceException(DEPT_NOT_ENABLE);
            }
        });
    }

    @Override
    public List<Department> getSimpleDepts(Collection<Long> ids) {
        return deptMapper.selectBatchIds(ids);
    }

    /**
     * 如果部门发生变化，从数据库中获取最新的全量部门。
     * 如果未发生变化，则返回空
     *
     * @param maxUpdateTime 当前部门的最大更新时间
     * @return 部门列表
     */
    protected List<Department> loadDeptIfUpdate(Date maxUpdateTime) {
        // 第一步，判断是否要更新。
        if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
            log.info("[loadMenuIfUpdate][首次加载全量部门]");
        } else { // 判断数据库中是否有更新的部门
            if (deptMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
                return null;
            }
            log.info("[loadMenuIfUpdate][增量加载全量部门]");
        }
        // 第二步，如果有更新，则从数据库加载所有部门
        return deptMapper.selectList();
    }

    /**
     * 递归获取所有的子部门，添加到 result 结果
     *
     * @param result         结果
     * @param parentId       父编号
     * @param recursiveCount 递归次数
     * @param parentDeptMap  父部门 Map，使用缓存，避免变化
     */
    private void getDeptsByParentIdFromCache(List<Department> result, Long parentId,
                                             int recursiveCount,
                                             Multimap<Long, Department> parentDeptMap) {
        // 递归次数为 0，结束！
        if (recursiveCount == 0) {
            return;
        }
        // 获得子部门
        Collection<Department> depts = parentDeptMap.get(parentId);
        if (CollUtils.isEmpty(depts)) {
            return;
        }
        result.addAll(depts);
        // 继续递归
        depts.forEach(dept -> getDeptsByParentIdFromCache(result, dept.getId(),
            recursiveCount - 1, parentDeptMap));
    }

    private void checkCreateOrUpdate(Long id, Long parentId, String name) {
        // 校验自己存在
        checkDeptExists(id);
        // 校验父部门的有效性
        checkParentDeptEnable(id, parentId);
        // 校验部门名的唯一性
        checkDeptNameUnique(id, parentId, name);
    }

    private void checkDeptExists(Long id) {
        if (id == null) {
            return;
        }
        Department dept = deptMapper.selectById(id);
        if (dept == null) {
            throw new ServiceException(DEPT_NOT_FOUND);
        }
    }

    private void checkParentDeptEnable(Long id, Long parentId) {
        if (parentId == null || DeptId.ROOT.getId().equals(parentId)) {
            return;
        }
        // 不能设置自己为父部门
        if (parentId.equals(id)) {
            throw new ServiceException(DEPT_PARENT_ERROR);
        }
        // 父岗位不存在
        Department dept = deptMapper.selectById(parentId);
        if (dept == null) {
            throw new ServiceException(DEPT_PARENT_NOT_EXITS);
        }
        // 父部门被禁用
        if (!CommonStatus.VALID.getValue().equals(dept.getStatus())) {
            throw new ServiceException(DEPT_NOT_ENABLE);
        }
        // 父部门不能是原来的子部门
        List<Department> children = this.getDeptsByParentIdFromCache(id, true);
        if (children.stream().anyMatch(dept1 -> dept1.getId().equals(parentId))) {
            throw new ServiceException(DEPT_PARENT_IS_CHILD);
        }
    }

    private void checkDeptNameUnique(Long id, Long parentId, String name) {
        Department menu = deptMapper.selectByParentIdAndName(parentId, name);
        if (menu == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw new ServiceException(DEPT_NAME_DUPLICATE);
        }
        if (!menu.getId().equals(id)) {
            throw new ServiceException(DEPT_NAME_DUPLICATE);
        }
    }

}
