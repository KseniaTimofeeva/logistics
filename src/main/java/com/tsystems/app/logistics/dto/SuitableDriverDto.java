package com.tsystems.app.logistics.dto;

import java.util.List;

/**
 * Created by ksenia on 17.10.2017.
 */
public class SuitableDriverDto {

    private List<DriverDto> drivers;
    private List<DriverDto> notSuitableDrivers;


    public List<DriverDto> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDto> drivers) {
        this.drivers = drivers;
    }

    public List<DriverDto> getNotSuitableDrivers() {
        return notSuitableDrivers;
    }

    public void setNotSuitableDrivers(List<DriverDto> notSuitableDrivers) {
        this.notSuitableDrivers = notSuitableDrivers;
    }
}
