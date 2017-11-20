package com.tsystems.app.logistics.dao.api;

import com.tsystems.app.logistics.entity.BaseEntity;

/**
 * Created by ksenia on 12.10.2017.
 */
public interface GenericDao<T extends BaseEntity> {
    T create(T t);

    T update(T t);

    T delete(T t);

    T deleteById(Long id);

    T findOneById(Long id);
}
