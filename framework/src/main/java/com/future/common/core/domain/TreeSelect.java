package com.future.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.future.common.core.domain.entity.Department;
import com.future.common.core.domain.entity.Resource;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 树结构实体类
 *
 * @author JonZhang
 */
@Data
public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    // 节点ID
    private Long id;

    // 节点名称
    private String label;

    // 子节点
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }

    public TreeSelect(Department dept) {
        this.id = dept.getId();
        this.label = dept.getName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(Resource resource) {
        this.id = resource.getId();
        this.label = resource.getName();
        this.children = resource.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

}
