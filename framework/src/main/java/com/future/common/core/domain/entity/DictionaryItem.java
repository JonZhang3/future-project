package com.future.common.core.domain.entity;

import com.future.common.constant.enums.State;
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
@Table(name = "sys_dict_item")
public class DictionaryItem extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("字典标签")
    @Column(nullable = false, length = 100)
    private String label;
    
    @Comment("字典值")
    @Column(nullable = false, length = 100)
    private String value;
    
    @Comment("所属字典")
    @Column(length = 100)
    private String dict;
    
    @Comment("样式属性")
    @Column(length = 100)
    private String cssClass;
    
    @Comment("表格回显样式")
    @Column(length = 100)
    private String listClass;
    
    @Comment("是否默认 Y是 N否")
    @Column(columnDefinition = "char(1) NOT NULL DEFAULT 'N'")
    private String isDefault = "N";
    
    @Comment("排序")
    @Column(columnDefinition = "int(4) NOT NULL DEFAULT 1")
    private Integer sortNum = 1;

    @Comment("状态 1-正常 0-停用 -1-删除")
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT 1")
    private State state;
    
}
