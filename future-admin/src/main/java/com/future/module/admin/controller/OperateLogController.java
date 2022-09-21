package com.future.module.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.CollUtils;
import com.future.framework.common.utils.ExcelUtils;
import com.future.framework.common.utils.MapUtils;
import com.future.module.system.domain.convert.OperateLogConvert;
import com.future.module.system.domain.entity.OperationLog;
import com.future.module.system.domain.entity.User;
import com.future.module.system.domain.query.logger.OperateLogExportQuery;
import com.future.module.system.domain.query.logger.OperateLogPageQuery;
import com.future.module.system.domain.vo.logger.OperateLogExcelVO;
import com.future.module.system.domain.vo.logger.OperateLogRespVO;
import com.future.module.system.service.OperateLogService;
import com.future.module.system.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.future.framework.common.constant.enums.OperateType.EXPORT;

//@Api(tags = "管理后台 - 操作日志")
@RestController
@RequestMapping("/admin-api/system/operate-log")
@Validated
public class OperateLogController {

    @Resource
    private OperateLogService operateLogService;
    @Resource
    private UserService userService;

    @GetMapping("/page")
//    @ApiOperation("查看操作日志分页列表")
    @SaCheckPermission("system:operate-log:query")
    public R<PageResult<OperateLogRespVO>> pageOperateLog(@Valid OperateLogPageQuery query) {
        PageResult<OperationLog> pageResult = operateLogService.getOperateLogPage(query);

        // 获得拼接需要的数据
        Collection<Long> userIds = CollUtils.convertList(pageResult.getList(), OperationLog::getUserId);
        Map<Long, User> userMap = userService.getUserMap(userIds);
        // 拼接数据
        List<OperateLogRespVO> list = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(operationLog -> {
            OperateLogRespVO respVO = OperateLogConvert.INSTANCE.convert(operationLog);
            list.add(respVO);
            // 拼接用户信息
            MapUtils.findAndThen(userMap, operationLog.getUserId(), user -> respVO.setUserNickname(user.getNickname()));
        });
        return R.ok(new PageResult<>(list, pageResult.getTotal()));
    }

//    @ApiOperation("导出操作日志")
    @GetMapping("/export")
    @SaCheckPermission("system:operate-log:export")
    @OperateLog(type = EXPORT)
    public void exportOperateLog(HttpServletResponse response, @Valid OperateLogExportQuery query) throws IOException {
        List<OperationLog> list = operateLogService.getOperateLogs(query);

        // 获得拼接需要的数据
        Collection<Long> userIds = CollUtils.convertList(list, OperationLog::getUserId);
        Map<Long, User> userMap = userService.getUserMap(userIds);
        // 拼接数据
        List<OperateLogExcelVO> excelDataList = OperateLogConvert.INSTANCE.convertToExcelList(list, userMap);
        // 输出
        ExcelUtils.write(response, "操作日志.xls", "数据列表", OperateLogExcelVO.class, excelDataList);
    }

}
