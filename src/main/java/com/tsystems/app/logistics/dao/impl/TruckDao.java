package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.dao.impl.GenericDaoImpl;
import com.tsystems.app.logistics.entity.Truck;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ksenia on 11.10.2017.
 */
@Repository
public class TruckDao extends GenericDaoImpl<Truck> {

    public List<Truck> getAllTrucks() {
        return em.createNamedQuery(Truck.GET_ALL_TRUCKS, Truck.class)
                .getResultList();
    }

    public List<Truck> getSuitableTrucks(Float maxTotalWeight) {
        return em.createNamedQuery(Truck.GET_SUITABLE_TRUCKS, Truck.class)
                .setParameter("maxTotalWeight", maxTotalWeight)
                .getResultList();
    }

    public List<Truck> newTruckValidate(String numberPlate) {
        return em.createNamedQuery(Truck.NEW_TRUCK_VALIDATE, Truck.class)
                .setParameter("numberPlate", numberPlate)
                .getResultList();
    }
}
