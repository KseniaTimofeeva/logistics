package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.dao.api.GenericDao;
import com.tsystems.app.logistics.entity.BaseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by ksenia on 11.10.2017.
 */
@Repository
public class GenericDaoImpl<T extends BaseEntity> implements GenericDao<T> {

    private Class<T> entityClass;

    @PersistenceContext
    EntityManager em;

    public final void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T create(T entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    @Override
    public T update(T t) {
        return em.merge(t);
    }

    @Override
    public void delete(T t) {
        t.setVisible(false);
        em.merge(t);
    }

    @Override
    public void deleteById(Long id) {
        T entity = findOneById(id);
        delete(entity);
    }

    @Override
    public T findOneById(Long id) {
        return em.find(entityClass, id);
    }


}
