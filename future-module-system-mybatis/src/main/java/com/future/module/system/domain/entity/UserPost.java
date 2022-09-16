package com.future.module.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.future.framework.mybatis.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户和岗位关联
 *
 * @author JonZhang
 */
@TableName("system_user_post")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserPost extends BaseEntity {

    /**
     * 自增主键
     */
    @TableId
    private Long id;
    /**
     * 用户 ID
     * 关联 {@link User#getId()}
     */
    private Long userId;
    /**
     * 角色 ID
     * 关联 {@link Post#getId()}
     */
    private Long postId;

}
