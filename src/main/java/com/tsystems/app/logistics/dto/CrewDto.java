package com.tsystems.app.logistics.dto;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
public class CrewDto {
    private Long id;
    private TruckDto truckDto;
    private List<DriverDto> driverDtoList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TruckDto getTruckDto() {
        return truckDto;
    }

    public void setTruckDto(TruckDto truckDto) {
        this.truckDto = truckDto;
    }

    public List<DriverDto> getDriverDtoList() {
        return driverDtoList;
    }

    public void setDriverDtoList(List<DriverDto> driverDtoList) {
        this.driverDtoList = driverDtoList;
    }
}
