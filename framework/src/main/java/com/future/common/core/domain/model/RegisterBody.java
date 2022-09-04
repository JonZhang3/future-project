package com.future.common.core.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 用户注册对象
 *
 * @author JonZhang
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegisterBody extends LoginBody {
}
