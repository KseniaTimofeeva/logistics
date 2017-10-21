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
    @Autowired
    private OrderConverter orderConverter;

    public TimeTrackDto toTimeTrackDto(TimeTrack timeTrack) {
        TimeTrackDto trackDto = new TimeTrackDto();
        trackDto.setDriver(driverConverter.toDriverShortDto(timeTrack.getUser()));
        trackDto.setDate(timeTrack.getDate());
        trackDto.setDriverAction(timeTrack.getDriverAction());
        trackDto.setOrder(orderConverter.toOrderDto(timeTrack.getOrder()));
        return trackDto;
    }
}
