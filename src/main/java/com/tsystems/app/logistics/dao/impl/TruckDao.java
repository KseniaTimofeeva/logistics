package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.entity.Crew;
import com.tsystems.app.logistics.entity.Truck;
import com.tsystems.app.logisticscommon.enums.OrderStatus;
import org.springframework.stereotype.Repository;

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

    public Long getTruckQty() {
        return em.createNamedQuery(Truck.GET_TRUCK_QTY, Long.class)
                .getSingleResult();
    }

    public Long getVacantTruckQty() {
        return em.createNamedQuery(Truck.GET_VACANT_TRUCK_QTY, Long.class)
                .getSingleResult();
    }

    public Long getOnOrderTruckQty() {
        return em.createNamedQuery(Truck.GET_ON_ORDER_TRUCK_QTY, Long.class)
                .getSingleResult();
    }

    public Long getNotWorkingTruckQty() {
        return em.createNamedQuery(Truck.GET_NOT_WORKING_TRUCK_QTY, Long.class)
                .getSingleResult();
    }

    public List<Truck> getTrucksFullInfo() {
        List<Truck> trucks = em.createNamedQuery(Truck.GET_ALL_TRUCKS, Truck.class)
                .getResultList();

        for (Truck truck : trucks) {
            if (truck.getCrews() != null) {
                List<Crew> crews = truck.getCrews();
                if (crews.size() == 1 && crews.get(0).getOrder().getStatus().equals(OrderStatus.FINISHED)) {
                    truck.setCrews(null);
                }
                if (crews.size() > 1) {
                    crews.removeIf(crew -> crew.getOrder().getStatus().equals(OrderStatus.NEW));
                }
            }
        }

        return trucks;
    }
}
