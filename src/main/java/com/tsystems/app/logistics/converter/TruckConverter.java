package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.TruckDto;
import com.tsystems.app.logistics.entity.Truck;
import com.tsystems.app.logisticscommon.TruckFullDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class TruckConverter {

    @Autowired
    private CityConverter cityConverter;
    @Autowired
    private CrewConverter crewConverter;

    public TruckDto toTruckDto(Truck truck) {
        if (truck == null) {
            return null;
        }
        TruckDto truckDto = new TruckDto();
        truckDto.setId(truck.getId());
        truckDto.setNumberPlate(truck.getNumberPlate());
        truckDto.setWorkingShift(truck.getWorkingShift());
        truckDto.setCapacity(truck.getCapacity());
        truckDto.setFunctioning(truck.getFunctioning());
        truckDto.setOnOrder(truck.getOnOrder());
        if (truck.getCurrentCity() != null) {
            truckDto.setCurrentCity(cityConverter.toCityDto(truck.getCurrentCity()));
        }
        return truckDto;
    }

    public List<TruckDto> toTruckDtoList(List<Truck> trucks) {
        return trucks
                .stream()
                .map(truck -> toTruckDto(truck))
                .collect(Collectors.toList());
    }

    public List<TruckFullDto> toTruckFullDtoList(List<Truck> trucks) {
        return trucks
                .stream()
                .map(truck -> toTruckFullDto(truck))
                .collect(Collectors.toList());
    }

    public TruckFullDto toTruckFullDto(Truck truck) {
        TruckFullDto truckFullDto = new TruckFullDto();
        truckFullDto.setId(truck.getId());
        truckFullDto.setNumberPlate(truck.getNumberPlate());
        truckFullDto.setWorkingShift(truck.getWorkingShift());
        truckFullDto.setCapacity(truck.getCapacity());
        truckFullDto.setFunctioning(truck.getFunctioning());
        truckFullDto.setOnOrder(truck.getOnOrder());
        if (truck.getCurrentCity() != null) {
            truckFullDto.setCurrentCity(cityConverter.toCityDto(truck.getCurrentCity()));
        }

        if (truck.getCrews() != null && truck.getCrews().size() > 0) {
            truckFullDto.setCrew(crewConverter.toCrewDriverInfoDto(truck.getCrews().get(0)));
        }
        return truckFullDto;
    }
}
