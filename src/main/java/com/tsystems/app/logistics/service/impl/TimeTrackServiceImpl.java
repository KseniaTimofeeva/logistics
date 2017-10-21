package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.DriverConverter;
import com.tsystems.app.logistics.converter.TimeTrackConverter;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dao.impl.TimeTrackDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.TimeTrackDto;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.TimeTrack;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.DriverAction;
import com.tsystems.app.logistics.entity.enums.OrderStatus;
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
public class TimeTrackServiceImpl implements TimeTrackService {

    private TimeTrackDao trackDao;
    private UserDao userDao;
    private OrderDao orderDao;

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

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        orderDao.setEntityClass(Order.class);
    }


    @Override
    public void addNewTimeTrack(String login, TimeTrackDto trackDto, DriverAction lastAction) {

        User driver = userDao.getUserByLogin(login).get(0);
        Order order = orderDao.findOneById(trackDto.getOrder().getId());

        //check if the order is new
        //if yes, change status to IN_PROCESS
        if (trackDto.getDriverAction().equals(DriverAction.START_WORKING_SHIFT)) {
            List<TimeTrack> orderTrackList = trackDao.getTracksForOrder(trackDto.getOrder().getId());
            if (orderTrackList.isEmpty()) {
                order.setStatus(OrderStatus.IN_PROCESS);
                order = orderDao.update(order);
            }
        }

        TimeTrack endTimeTrack = new TimeTrack();
        endTimeTrack.setUser(driver);
        endTimeTrack.setDate(new Timestamp(System.currentTimeMillis()));
        endTimeTrack.setOrder(order);

        switch (lastAction) {
            case START_DRIVING:
                endTimeTrack.setDriverAction(DriverAction.END_DRIVING);
                break;
            case START_SECOND:
                endTimeTrack.setDriverAction(DriverAction.END_SECOND);
                break;
            case START_LOAD_UNLOAD:
                endTimeTrack.setDriverAction(DriverAction.END_LOAD_UNLOAD);
                break;
            case START_RELAX:
                endTimeTrack.setDriverAction(DriverAction.END_RELAX);
                break;
        }
        trackDao.create(endTimeTrack);

        TimeTrack timeTrack = new TimeTrack();
        timeTrack.setUser(driver);
        timeTrack.setDriverAction(trackDto.getDriverAction());
        timeTrack.setDate(new Timestamp(System.currentTimeMillis()));
        timeTrack.setOrder(order);

        trackDao.create(timeTrack);


    }

    @Override
    public TimeTrackDto getLastActionForOrder(String login, Long orderId) {
        List<TimeTrack> timeTrackList = trackDao.getLastActionForOrder(login, orderId);
        if (timeTrackList.isEmpty()) {
            return null;
        }
        return trackConverter.toTimeTrackDto(timeTrackList.get(0));
    }
}
