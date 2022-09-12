package com.future.framework.mybatis.query;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class QueryWrapper<T> extends com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T> {

    public QueryWrapper<T> likeIfPresent(String column, String val) {
        if (StringUtils.hasText(val)) {
            return (QueryWrapper<T>) super.like(column, val);
        }
        return this;
    }

    public QueryWrapper<T> inIfPresent(String column, Collection<?> values) {
        if (!CollectionUtils.isEmpty(values)) {
            return (QueryWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public QueryWrapper<T> inIfPresent(String column, Object... values) {
        if (!ArrayUtils.isEmpty(values)) {
            return (QueryWrapper<T>) super.in(column, values);
        }
        return this;
    }

    public QueryWrapper<T> eqIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapper<T>) super.eq(column, val);
        }
        return this;
    }

    public QueryWrapper<T> neIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapper<T>) super.ne(column, val);
        }
        return this;
    }

    public QueryWrapper<T> gtIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapper<T>) super.gt(column, val);
        }
        return this;
    }

    public QueryWrapper<T> geIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapper<T>) super.ge(column, val);
        }
        return this;
    }

    public QueryWrapper<T> ltIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapper<T>) super.lt(column, val);
        }
        return this;
    }

    public QueryWrapper<T> leIfPresent(String column, Object val) {
        if (val != null) {
            return (QueryWrapper<T>) super.le(column, val);
        }
        return this;
    }

    public QueryWrapper<T> betweenIfPresent(String column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (QueryWrapper<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (QueryWrapper<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (QueryWrapper<T>) le(column, val2);
        }
        return this;
    }

    public QueryWrapper<T> betweenIfPresent(String column, Object[] values) {
        if (values != null && values.length != 0 && values[0] != null && values[1] != null) {
            return (QueryWrapper<T>) super.between(column, values[0], values[1]);
        }
        if (values != null && values.length != 0 && values[0] != null) {
            return (QueryWrapper<T>) ge(column, values[0]);
        }
        if (values != null && values.length != 0 && values[1] != null) {
            return (QueryWrapper<T>) le(column, values[1]);
        }
        return this;
    }

    // ========== 重写父类方法，方便链式调用 ==========

    @Override
    public QueryWrapper<T> eq(boolean condition, String column, Object val) {
        super.eq(condition, column, val);
        return this;
    }

    @Override
    public QueryWrapper<T> eq(String column, Object val) {
        super.eq(column, val);
        return this;
    }

    @Override
    public QueryWrapper<T> orderByDesc(String column) {
        super.orderByDesc(true, column);
        return this;
    }

    @Override
    public QueryWrapper<T> last(String lastSql) {
        super.last(lastSql);
        return this;
    }

    @Override
    public QueryWrapper<T> in(String column, Collection<?> coll) {
        super.in(column, coll);
        return this;
    }

}
