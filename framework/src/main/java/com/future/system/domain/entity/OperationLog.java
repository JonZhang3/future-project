package com.future.system.domain.entity;

import com.future.common.constant.enums.BusinessType;
import com.future.common.constant.enums.OperatingState;
import com.future.common.constant.enums.OperatorType;
import com.future.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_operation_log")
@org.hibernate.annotations.Table(appliesTo = "sys_operation_log", comment = "操作日志")
public class OperationLog extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Comment("操作模块")
    @Column(length = 50)
    private String title;
    
    @Comment("业务类型")
    @Column(columnDefinition = "tinyint(2) NOT NULL DEFAULT 0")
    private BusinessType businessType;
    
    @Comment("操作用户ID")
    private Long userId;
    
    @Comment("方法名称")
    @Column(length = 100)
    private String method;
    
    @Comment("请求方式")
    @Column(length = 10)
    private String requestMethod;
    
    @Comment("请求人类型")
    @Column(columnDefinition = "tinyint(2) NOT NULL DEFAULT 0")
    private OperatorType operatorType;
    
    @Comment("请求URL")
    private String url;
    
    @Comment("请求IP")
    @Column(length = 128)
    private String ip;
    
    @Comment("请求参数")
    @Column(columnDefinition = "text")
    private String queryParam;
    
    @Comment("返回参数")
    @Column(columnDefinition = "text")
    private String response;

    @Comment("登录状态 1成功 0失败")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private OperatingState state = OperatingState.SUCCESS;
    
    @Comment("错误消息")
    @Column(length = 2000)
    private String errorMessage;
    
}
