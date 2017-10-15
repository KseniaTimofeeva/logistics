package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.DriverDto;

import java.util.List;

/**
 * Created by ksenia on 08.10.2017.
 */

public interface DriverService {

    void addNewDriver(DriverDto driverDto);

    List<DriverDto> getAllDrivers();

    void deleteDriver(Long id);

    DriverDto getDriverById(Long id);

    void processDriver(DriverDto driverDto);

    void updateDriver(DriverDto driverDto);
}
