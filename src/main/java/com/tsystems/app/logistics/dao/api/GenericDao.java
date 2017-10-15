package com.tsystems.app.logistics.dao.api;

import com.tsystems.app.logistics.entity.BaseEntity;

/**
 * Created by ksenia on 12.10.2017.
 */
public interface GenericDao<T extends BaseEntity> {
    T create(T t);

    T update(T t);

    void delete(T t);

    void deleteById(Long id);

    T findOneById(Long id);
}
