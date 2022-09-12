package com.future.module.system.dao;

import com.future.framework.mybatis.mapper.BaseMapper;
import com.future.framework.mybatis.query.LambdaQueryWrapper;
import com.future.module.system.domain.entity.Department;
import com.future.module.system.domain.query.dept.DeptListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapper<Department> {

    default List<Department> selectList(DeptListQuery query) {
        return selectList(new LambdaQueryWrapper<Department>()
            .likeIfPresent(Department::getName, query.getName())
            .eqIfPresent(Department::getStatus, query.getStatus()));
    }

    default Department selectByParentIdAndName(Long parentId, String name) {
        return selectOne(new LambdaQueryWrapper<Department>()
            .eq(Department::getParentId, parentId)
            .eq(Department::getName, name));
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(Department::getParentId, parentId);
    }

    @Select("SELECT COUNT(*) FROM system_dept WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(Date maxUpdateTime);

}
