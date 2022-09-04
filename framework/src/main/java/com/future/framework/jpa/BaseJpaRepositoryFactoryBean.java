package com.future.framework.jpa;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseJpaRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable> 
    extends JpaRepositoryFactoryBean<T, S, ID> {
    
    public BaseJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected @NotNull RepositoryFactorySupport createRepositoryFactory(@NotNull EntityManager em) {
        return new BaseJpaRepositoryFactory<>(em);
    }

    private static class BaseJpaRepositoryFactory<S, ID extends Serializable> extends JpaRepositoryFactory {

        public BaseJpaRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected @NotNull JpaRepositoryImplementation<?, ?> getTargetRepository(@NotNull RepositoryInformation information,
                                                                                 @NotNull EntityManager em) {
            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
            return new BaseJpaRepositoryImpl<>(entityInformation, em);
        }

        @Override
        protected @NotNull Class<?> getRepositoryBaseClass(@NotNull RepositoryMetadata metadata) {
            return BaseJpaRepositoryImpl.class;
        }
    }
    
}
