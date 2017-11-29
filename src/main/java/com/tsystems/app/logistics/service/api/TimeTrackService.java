package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.TimeTrackDto;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.User;

/**
 * Created by ksenia on 20.10.2017.
 */
public interface TimeTrackService {

    void addNewTimeTrack(String login, TimeTrackDto trackDto);

    TimeTrackDto getLastActionForOrder(String login, Long orderId);

    void addTimeTrackRepair(Order order, User driver);
}
