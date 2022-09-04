package com.future.framework.jpa;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * 自定义 JPA 接口
 * 
 * @author JonZhang
 */
@NoRepositoryBean
public interface BaseJpaRepository<T, ID> extends JpaRepository<T, ID> {

    @NotNull EntityManager entityManager();

    <S extends T> S insert(S entity);

    <S extends T> S update(S entity);

    <S extends T> S find(String sql, Object... params);

    <S extends T> List<S> findAll(String sql, Object... params);

    Map<String, Object> findToMap(String sql, Object... params);

    List<Map<String, Object>> findAllToMap(String sql, Object... params);

    Page<T> findAll(int page, int size, Sort sort);

    Page<T> findAll(int page, int size, Sort sort, Specification<T> specification);

}
