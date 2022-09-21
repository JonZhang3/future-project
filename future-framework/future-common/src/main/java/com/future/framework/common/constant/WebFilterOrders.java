package com.future.framework.common.constant;

/**
 * Web 过滤器顺序的枚举类，保证过滤器按照符合我们的预期
 * 考虑到每个 starter 都需要用到该工具类，所以放到 common 模块下的 enums 包下
 *
 * @author JonZhang
 */
public interface WebFilterOrders {

    int CORS_FILTER = Integer.MIN_VALUE;

    int SA_TOKEN_FILTER = 1;

}
