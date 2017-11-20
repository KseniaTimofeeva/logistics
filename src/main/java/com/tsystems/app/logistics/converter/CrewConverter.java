package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.CrewDriverProfileDto;
import com.tsystems.app.logistics.dto.CrewDto;
import com.tsystems.app.logistics.entity.Crew;
import com.tsystems.app.logisticscommon.CrewDriverInfoDto;
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
    @Autowired
    private OrderConverter orderConverter;

    public CrewDto toCrewDto(Crew crew) {
        CrewDto crewDto = new CrewDto();
        crewDto.setId(crew.getId());
        if (crew.getTruck() != null) {
            crewDto.setTruck(truckConverter.toTruckDto(crew.getTruck()));
        }
        if (crew.getUsers() != null) {
            crewDto.setUsers(driverConverter.toDriverDtoList(crew.getUsers()));
        }
        return crewDto;
    }

    public CrewDriverProfileDto toCrewDriverProfileDto(Crew crew) {
        if (crew == null) {
            return null;
        }
        CrewDriverProfileDto crewDriverProfileDto = new CrewDriverProfileDto();
        crewDriverProfileDto.setId(crew.getId());
        crewDriverProfileDto.setOrder(orderConverter.toOrderDto(crew.getOrder()));
        crewDriverProfileDto.setTruck(truckConverter.toTruckDto(crew.getTruck()));
        crewDriverProfileDto.setUsers(driverConverter.toDriverShortDtoList(crew.getUsers()));
        return crewDriverProfileDto;
    }

    public CrewDriverInfoDto toCrewDriverInfoDto(Crew crew) {
        if (crew == null) {
            return null;
        }
        if (crew.getUsers() == null || crew.getUsers().isEmpty()) {
            return null;
        }
        CrewDriverInfoDto crewDriverInfoDto = new CrewDriverInfoDto();
        crewDriverInfoDto.setId(crew.getId());
        crewDriverInfoDto.setUsers(driverConverter.toDriverShortDtoList(crew.getUsers()));
        return crewDriverInfoDto;
    }
}
