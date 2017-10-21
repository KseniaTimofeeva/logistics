package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.DriverConverter;
import com.tsystems.app.logistics.converter.TimeTrackConverter;
import com.tsystems.app.logistics.dao.impl.TimeTrackDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.TimeTrackDto;
import com.tsystems.app.logistics.entity.TimeTrack;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.DriverAction;
import com.tsystems.app.logistics.service.api.TimeTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ksenia on 20.10.2017.
 */
@Service
@Transactional
public class TimeTrackServiceImpl implements TimeTrackService{

    private TimeTrackDao trackDao;
    private UserDao userDao;

    @Autowired
    private TimeTrackConverter trackConverter;

    @Autowired
    public void setTrackDao(TimeTrackDao trackDao) {
        this.trackDao = trackDao;
        trackDao.setEntityClass(TimeTrack.class);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        userDao.setEntityClass(User.class);
    }

    @Override
    public TimeTrackDto getCurrentAction(String login) {
        TimeTrack currentTimeTrack = trackDao.getCurrentAction(login).get(0);
        return trackConverter.toCurrentTimeTrackDto(currentTimeTrack);
    }

    @Override
    public void addNewTimeTrack(String login, TimeTrackDto trackDto, TimeTrackDto currentAction) {

        User driver = userDao.getUserByLogin(login).get(0);

        /*if (currentAction != null) {
            if (!currentAction.getCurrentAction().equals(DriverAction.START_WORKING_SHIFT) && !currentAction.getCurrentAction().equals(DriverAction.END_WORKING_SHIFT)) {
                TimeTrack endTimeTrack = new TimeTrack();
                endTimeTrack.setUser(driver);
                endTimeTrack.setDate(new Timestamp(System.currentTimeMillis()));
                endTimeTrack.setDriverAction();
            }

        }

        TimeTrack timeTrack = new TimeTrack();
        timeTrack.setUser(users.get(0));


        timeTrack.setDriverAction(trackDto.getDriverAction());
        timeTrack.setDate(new Timestamp(System.currentTimeMillis()));
        trackDao.create(timeTrack);*/
    }
}
