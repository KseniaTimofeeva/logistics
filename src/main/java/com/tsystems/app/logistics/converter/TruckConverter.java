package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.TruckDto;
import com.tsystems.app.logistics.entity.Truck;
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
}
