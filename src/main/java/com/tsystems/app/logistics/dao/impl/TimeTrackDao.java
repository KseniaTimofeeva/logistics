package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.entity.TimeTrack;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ksenia on 20.10.2017.
 */
@Repository
public class TimeTrackDao extends GenericDaoImpl<TimeTrack> {

    public List<TimeTrack> getCurrentAction(String login) {
        return em.createNamedQuery(TimeTrack.GET_CURRENT_ACTION_BY_DRIVER_LOGIN, TimeTrack.class)
                .setMaxResults(1)
                .getResultList();
    }
}
