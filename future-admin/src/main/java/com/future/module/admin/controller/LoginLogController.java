package com.future.module.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.ExcelUtils;
import com.future.module.system.domain.convert.LoginLogConvert;
import com.future.module.system.domain.entity.LoginLog;
import com.future.module.system.domain.query.logger.LoginLogExportQuery;
import com.future.module.system.domain.query.logger.LoginLogPageQuery;
import com.future.module.system.domain.vo.logger.LoginLogExcelVO;
import com.future.module.system.domain.vo.logger.LoginLogRespVO;
import com.future.module.system.service.LoginLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.future.framework.common.constant.enums.OperateType.EXPORT;

//@Api(tags = "管理后台 - 登录日志")
@RestController
@RequestMapping("/admin-api/system/login-log")
@Validated
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @GetMapping("/page")
//    @ApiOperation("获得登录日志分页列表")
    @SaCheckPermission("system:login-log:query")
    public R<PageResult<LoginLogRespVO>> getLoginLogPage(@Valid LoginLogPageQuery query) {
        PageResult<LoginLog> page = loginLogService.getLoginLogPage(query);
        return R.ok(LoginLogConvert.INSTANCE.convertPage(page));
    }

    @GetMapping("/export")
//    @ApiOperation("导出登录日志 Excel")
    @SaCheckPermission("system:login-log:export")
    @OperateLog(type = EXPORT)
    public void exportLoginLog(HttpServletResponse response, @Valid LoginLogExportQuery query) throws IOException {
        List<LoginLog> list = loginLogService.getLoginLogList(query);
        // 拼接数据
        List<LoginLogExcelVO> data = LoginLogConvert.INSTANCE.convertList(list);
        // 输出
        ExcelUtils.write(response, "登录日志.xls", "数据列表", LoginLogExcelVO.class, data);
    }


}
