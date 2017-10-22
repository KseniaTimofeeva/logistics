package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.entity.TimeTrack;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ksenia on 20.10.2017.
 */
@Repository
public class TimeTrackDao extends GenericDaoImpl<TimeTrack> {

    public List<TimeTrack> getLastActionForOrder(String login, Long orderId) {
        return em.createNamedQuery(TimeTrack.GET_LAST_ACTION_BY_DRIVER_LOGIN, TimeTrack.class)
                .setParameter("login", login)
                .setParameter("orderId", orderId)
                .setMaxResults(1)
                .getResultList();
    }

    public List<TimeTrack> getTracksForOrder(Long orderId) {
        return em.createNamedQuery(TimeTrack.GET_TRACKS_FOR_ORDER, TimeTrack.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    public List<TimeTrack> getTracksInCurrentMonth(Long driverId, Timestamp firstDayOfMonth) {
        return em.createNamedQuery(TimeTrack.GET_TRACKS_IN_CURRENT_MONTH, TimeTrack.class)
                .setParameter("driverId", driverId)
                .setParameter("firstDayOfMonth", firstDayOfMonth)
                .getResultList();
    }

    public List<TimeTrack> getLastDriverTrack(Long driverId) {
        return em.createNamedQuery(TimeTrack.GET_LAST_DRIVER_TRACK, TimeTrack.class)
                .setParameter("driverId", driverId)
                .setMaxResults(1)
                .getResultList();
    }
}
