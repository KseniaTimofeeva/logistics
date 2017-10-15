package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.TruckDto;

import java.util.List;

/**
 * Created by ksenia on 11.10.2017.
 */
public interface TruckService {

    void addNewTruck(TruckDto truckDto);

    List<TruckDto> getAllTrucks();

    void deleteTruck(Long id);

    TruckDto getTruckById(Long id);

    void processTruck(TruckDto truckDto);

    void updateTruck(TruckDto truckDto);
}
