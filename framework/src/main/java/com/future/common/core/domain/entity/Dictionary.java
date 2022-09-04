package com.future.common.core.domain.entity;

import com.future.common.constant.enums.State;
import com.future.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_dict")
@org.hibernate.annotations.Table(appliesTo = "sys_dict", comment = "字典表")
public class Dictionary extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Comment("字典名称")
    @Column(length = 100)
    private String name = "";
    
    @Comment("字典代码")
    @Column(length = 100, unique = true)
    private String code = "";

    @Comment("状态 1-正常 0-停用 -1-删除")
    private State state;

    @Comment("描述")
    @Column(length = 500)
    private String remark;
    
}
