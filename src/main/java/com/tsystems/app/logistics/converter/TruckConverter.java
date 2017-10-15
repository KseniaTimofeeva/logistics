package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.TruckDto;
import com.tsystems.app.logistics.entity.Truck;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class TruckConverter {

    public TruckDto toTruckDto(Truck truck) {
        TruckDto truckDto = new TruckDto();
        truckDto.setId(truck.getId());
        truckDto.setNumberPlate(truck.getNumberPlate());
        truckDto.setWorkingShift(truck.getWorkingShift());
        truckDto.setCapacity(truck.getCapacity());
        truckDto.setFunctioning(truck.getFunctioning());
        return truckDto;
    }

    public List<TruckDto> toTruckDtoList(List<Truck> trucks) {
        return trucks
                .stream()
                .map(truck -> toTruckDto(truck))
                .collect(Collectors.toList());
    }
}
