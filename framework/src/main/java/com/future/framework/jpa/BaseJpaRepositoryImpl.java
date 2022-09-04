package com.future.framework.jpa;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 * 自定义 JPA 接口的实现
 *
 * @author JonZhang
 */
@Transactional(readOnly = true)
public class BaseJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    private final EntityManager em;

    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }

    public BaseJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    /**
     * 获取 EntityManager
     *
     * @see EntityManager
     */
    @Override
    public @NotNull EntityManager entityManager() {
        return this.em;
    }

    @Transactional
    @Override
    public <S extends T> S insert(@NotNull S entity) {
        Assert.notNull(entity, "Entity must not be null.");

        this.em.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public <S extends T> S update(S entity) {
        Assert.notNull(entity, "Entity must not be null.");

        return this.em.merge(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends T> @Nullable S find(String sql, Object... params) {
        Query query = createNativeQuery(getDomainClass(), sql, params);
        return (S) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends T> List<S> findAll(String sql, Object... params) {
        Query query = createNativeQuery(getDomainClass(), sql, params);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable Map<String, Object> findToMap(String sql, Object... params) {
        Query query = createNativeQuery(sql, params);
        return (Map<String, Object>) query.unwrap(NativeQueryImpl.class)
            .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
            .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findAllToMap(String sql, Object... params) {
        Query query = createNativeQuery(sql, params);
        return query.unwrap(NativeQueryImpl.class)
            .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
            .getResultList();
    }

    @Override
    public Page<T> findAll(int page, int size, Sort sort) {
        if (page <= 0) {
            page = 1;
        }
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return this.findAll(pageRequest);
    }

    @Override
    public Page<T> findAll(int page, int size, Sort sort, Specification<T> specification) {
        if (page <= 0) {
            page = 1;
        }
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        if (specification == null) {
            return this.findAll(pageRequest);
        }
        return this.findAll(specification, pageRequest);
    }

    private Query createNativeQuery(String sql, Object... params) {
        Query query = this.em.createNativeQuery(sql);
        fillQueryParams(query, params);
        return query;
    }

    private Query createNativeQuery(Class<T> domainClass, String sql, Object... params) {
        Query query = this.em.createNativeQuery(sql, domainClass);
        fillQueryParams(query, params);
        return query;
    }

    private void fillQueryParams(Query query, Object... params) {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
    }

}
