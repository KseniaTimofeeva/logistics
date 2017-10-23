package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.entity.PathPoint;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
@Repository
public class PathPointDao extends GenericDaoImpl<PathPoint> {

    public List<PathPoint> getAllPathPointsByOrderId(Long id) {
        return em.createNamedQuery(PathPoint.GET_ALL_POINTS_BY_ORDER_ID, PathPoint.class)
                .setParameter("orderId", id)
                .getResultList();
    }

    public List<PathPoint> getPathPointsWithCargoToUnload(Long orderId) {
        return em.createNamedQuery(PathPoint.GET_POINTS_WITH_CARGO_TO_UNLOAD, PathPoint.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
