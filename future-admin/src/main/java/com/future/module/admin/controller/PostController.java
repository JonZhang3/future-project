package com.future.module.admin.controller;

import com.future.framework.common.annotations.OperateLog;
import com.future.framework.common.constant.enums.CommonStatus;
import com.future.framework.common.domain.PageResult;
import com.future.framework.common.domain.R;
import com.future.framework.common.utils.ExcelUtils;
import com.future.module.system.domain.convert.PostConvert;
import com.future.module.system.domain.entity.Post;
import com.future.module.system.domain.query.dept.PostCreateQuery;
import com.future.module.system.domain.query.dept.PostExportQuery;
import com.future.module.system.domain.query.dept.PostPageQuery;
import com.future.module.system.domain.query.dept.PostUpdateQuery;
import com.future.module.system.domain.vo.dept.PostExcelVO;
import com.future.module.system.domain.vo.dept.PostRespVO;
import com.future.module.system.domain.vo.dept.PostSimpleRespVO;
import com.future.module.system.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.future.framework.common.constant.enums.OperateType.EXPORT;

//@Api(tags = "管理后台 - 岗位")
@RestController
@RequestMapping("/admin-api/system/post")
@Validated
public class PostController {

    @Resource
    private PostService postService;

    @PostMapping("/create")
//    @ApiOperation("创建岗位")
    @PreAuthorize("@ss.hasPermission('system:post:create')")
    public R<Long> createPost(@Valid @RequestBody PostCreateQuery query) {
        return R.ok(postService.createPost(query));
    }

    @PutMapping("/update")
//    @ApiOperation("修改岗位")
    @PreAuthorize("@ss.hasPermission('system:post:update')")
    public R<Boolean> updatePost(@Valid @RequestBody PostUpdateQuery query) {
        postService.updatePost(query);
        return R.ok(true);
    }

    @DeleteMapping("/delete")
//    @ApiOperation("删除岗位")
    @PreAuthorize("@ss.hasPermission('system:post:delete')")
    public R<Boolean> deletePost(@RequestParam("id") Long id) {
        postService.deletePost(id);
        return R.ok(true);
    }

    @GetMapping(value = "/get")
//    @ApiOperation("获得岗位信息")
//    @ApiImplicitParam(name = "id", value = "岗位编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public R<PostRespVO> getPost(@RequestParam("id") Long id) {
        return R.ok(PostConvert.INSTANCE.convert(postService.getPost(id)));
    }

    @GetMapping("/list-all-simple")
//    @ApiOperation(value = "获取岗位精简信息列表", notes = "只包含被开启的岗位，主要用于前端的下拉选项")
    public R<List<PostSimpleRespVO>> getSimplePosts() {
        // 获得岗位列表，只要开启状态的
        List<Post> list = postService.getPosts(null, Collections.singleton(CommonStatus.VALID.getValue()));
        // 排序后，返回给前端
        list.sort(Comparator.comparing(Post::getSort));
        return R.ok(PostConvert.INSTANCE.convertToSimpleList(list));
    }

    @GetMapping("/page")
//    @ApiOperation("获得岗位分页列表")
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public R<PageResult<PostRespVO>> getPostPage(@Validated PostPageQuery query) {
        return R.ok(PostConvert.INSTANCE.convertPage(postService.getPostPage(query)));
    }

    @GetMapping("/export")
//    @ApiOperation("岗位管理")
    @PreAuthorize("@ss.hasPermission('system:post:export')")
    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Validated PostExportQuery query) throws IOException {
        List<Post> posts = postService.getPosts(query);
        List<PostExcelVO> data = PostConvert.INSTANCE.convertToPostExcelList(posts);
        // 输出
        ExcelUtils.write(response, "岗位数据.xls", "岗位列表", PostExcelVO.class, data);
    }

}
