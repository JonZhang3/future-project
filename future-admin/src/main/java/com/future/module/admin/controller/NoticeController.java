package com.future.module.admin.controller;

import com.future.framework.common.domain.PageResult;
import com.future.framework.common.domain.R;
import com.future.module.system.domain.convert.NoticeConvert;
import com.future.module.system.domain.query.notice.NoticeCreateQuery;
import com.future.module.system.domain.query.notice.NoticePageQuery;
import com.future.module.system.domain.query.notice.NoticeUpdateQuery;
import com.future.module.system.domain.vo.notice.NoticeRespVO;
import com.future.module.system.service.NoticeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

//@Api(tags = "管理后台 - 通知公告")
@RestController
@RequestMapping("/admin-api/system/notice")
@Validated
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping("/create")
//    @ApiOperation("创建通知公告")
    @PreAuthorize("@ss.hasPermission('system:notice:create')")
    public R<Long> createNotice(@Valid @RequestBody NoticeCreateQuery query) {
        return R.ok(noticeService.createNotice(query));
    }

    @PutMapping("/update")
//    @ApiOperation("修改通知公告")
    @PreAuthorize("@ss.hasPermission('system:notice:update')")
    public R<Boolean> updateNotice(@Valid @RequestBody NoticeUpdateQuery query) {
        noticeService.updateNotice(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
//    @ApiOperation("删除通知公告")
//    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:notice:delete')")
    public R<Boolean> deleteNotice(@RequestParam("id") Long id) {
        noticeService.deleteNotice(id);
        return R.ok(true);
    }

    @GetMapping("/page")
//    @ApiOperation("获取通知公告列表")
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    public R<PageResult<NoticeRespVO>> pageNotices(@Validated NoticePageQuery query) {
        return R.ok(NoticeConvert.INSTANCE.convertPage(noticeService.pageNotices(query)));
    }

    @GetMapping("/get")
//    @ApiOperation("获得通知公告")
//    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    public R<NoticeRespVO> getNotice(@RequestParam("id") Long id) {
        return R.ok(NoticeConvert.INSTANCE.convert(noticeService.getNotice(id)));
    }

}
