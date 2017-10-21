package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.TimeTrackDto;

/**
 * Created by ksenia on 20.10.2017.
 */
public interface TimeTrackService {

    void addNewTimeTrack(String login, TimeTrackDto trackDto, TimeTrackDto currentAction);

    TimeTrackDto getCurrentAction(String login);
}
