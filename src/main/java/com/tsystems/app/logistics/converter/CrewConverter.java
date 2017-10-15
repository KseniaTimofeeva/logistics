package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.CrewDto;
import com.tsystems.app.logistics.entity.Crew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class CrewConverter {

    @Autowired
    private TruckConverter truckConverter;

    @Autowired
    private DriverConverter driverConverter;

    public CrewDto toCrewDto(Crew crew) {
        CrewDto crewDto = new CrewDto();
        crewDto.setId(crew.getId());
        if (crew.getTruck() != null) {
            crewDto.setTruckDto(truckConverter.toTruckDto(crew.getTruck()));
        }
        if (crew.getUsers() != null) {
            crewDto.setDriverDtoList(driverConverter.toDriverDtoList(crew.getUsers()));
        }
        return crewDto;
    }
}
