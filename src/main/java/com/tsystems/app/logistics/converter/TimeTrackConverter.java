package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.TimeTrackDto;
import com.tsystems.app.logistics.entity.TimeTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ksenia on 21.10.2017.
 */
@Component
public class TimeTrackConverter {

    @Autowired
    private DriverConverter driverConverter;

    public TimeTrackDto toCurrentTimeTrackDto(TimeTrack timeTrack) {
        TimeTrackDto trackDto = new TimeTrackDto();
        trackDto.setDriver(driverConverter.toDriverShortDto(timeTrack.getUser()));
        trackDto.setCurrentAction(timeTrack.getDriverAction());
        trackDto.setDate(timeTrack.getDate());
        return trackDto;
    }
}
