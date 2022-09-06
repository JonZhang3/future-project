package com.future.system.controller.monitor;

import com.future.common.annotation.Log;
import com.future.common.constant.enums.BusinessType;
import com.future.common.core.domain.R;
import com.future.system.service.OperationLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 操作日志
 *
 * @author JonZhang
 */
@RestController
@RequestMapping("/api/monitor/operlog")
public class OperationLogController {

    @Resource(type = OperationLogService.class)
    private OperationLogService operationLogService;

    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("/list")
    public R pageList() {
        return R.ok();
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response) {

    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable("ids") List<Long> ids) {
        operationLogService.deleteOperationLogByIds(ids);
        return R.ok();
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public R clean() {
        operationLogService.cleanOperationLog();
        return R.ok();
    }

}
