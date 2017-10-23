package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.entity.Cargo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
@Repository
public class CargoDao extends GenericDaoImpl<Cargo> {

    public List<Cargo> newCargoValidate(String number) {
        return em.createNamedQuery(Cargo.NEW_CARGO_VALIDATE, Cargo.class)
                .setParameter("number", number)
                .getResultList();
    }
}
