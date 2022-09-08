package com.future.common.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据库Entity基类。
 * 默认ID使用雪花ID生成，如果有其他需求，在特定的Entity类中重新声明ID字段即可
 *
 * @author JonZhang
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected static final String GENERATOR_STRATEGY = "com.future.framework.jpa.SnowflakeIdGenerator";
    
    @CreatedBy
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment("创建时间")
    @Column(name = "create_time", updatable = false)
    @CreatedDate
    private LocalDateTime createTime;

    @Comment("更新者")
    @LastModifiedBy
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment("更新时间")
    @Column(name = "update_time")
    @LastModifiedDate
    private LocalDateTime updateTime;

}
