package com.future.module.admin.controller;

import com.future.framework.common.domain.R;
import com.future.module.system.domain.convert.NoticeConvert;
import com.future.module.system.domain.query.notice.NoticeCreateQuery;
import com.future.module.system.domain.query.notice.NoticePageQuery;
import com.future.module.system.domain.query.notice.NoticeUpdateQuery;
import com.future.module.system.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "管理后台 - 通知公告")
@RestController
@RequestMapping("/system/notice")
@Validated
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping("/create")
    @ApiOperation("创建通知公告")
    @PreAuthorize("@ss.hasPermission('system:notice:create')")
    public R createNotice(@Valid @RequestBody NoticeCreateQuery query) {
        Long noticeId = noticeService.createNotice(query);
        return R.ok(noticeId);
    }

    @PutMapping("/update")
    @ApiOperation("修改通知公告")
    @PreAuthorize("@ss.hasPermission('system:notice:update')")
    public R updateNotice(@Valid @RequestBody NoticeUpdateQuery query) {
        noticeService.updateNotice(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除通知公告")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:notice:delete')")
    public R deleteNotice(@RequestParam("id") Long id) {
        noticeService.deleteNotice(id);
        return R.ok(true);
    }

    @GetMapping("/page")
    @ApiOperation("获取通知公告列表")
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    public R pageNotices(@Validated NoticePageQuery query) {
        return R.ok(NoticeConvert.INSTANCE.convertPage(noticeService.pageNotices(query)));
    }

    @GetMapping("/get")
    @ApiOperation("获得通知公告")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    public R getNotice(@RequestParam("id") Long id) {
        return R.ok(NoticeConvert.INSTANCE.convert(noticeService.getNotice(id)));
    }

}
