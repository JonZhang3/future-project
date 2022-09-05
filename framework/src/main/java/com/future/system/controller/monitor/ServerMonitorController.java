package com.future.system.controller.monitor;

import com.future.common.core.domain.R;
import com.future.common.core.domain.monitor.Server;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author JonZhang
 */
@RestController
@RequestMapping("/api/monitor/server")
public class ServerMonitorController {

    // TODO ss
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping
    public R getInfo() {
        Server server = new Server();
        server.init();
        return R.ok(server);
    }

}
