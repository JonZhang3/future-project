package com.future.common.core.domain.monitor;

import java.io.Serializable;

import lombok.Data;

@Data
public class Memory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

}
