package com.tsystems.app.logistics.dto;

import com.tsystems.app.logistics.entity.City;

import javax.validation.constraints.NotNull;

/**
 * Created by ksenia on 11.10.2017.
 */
public class TruckDto {
    private Long id;
    @NotNull
    private String numberPlate;
    @NotNull
    private Float workingShift;
    @NotNull
    private Float capacity;
    @NotNull
    private Boolean functioning;
    private Boolean onOrder;
    private CityDto currentCity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Float getWorkingShift() {
        return workingShift;
    }

    public void setWorkingShift(Float workingShift) {
        this.workingShift = workingShift;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Boolean getFunctioning() {
        return functioning;
    }

    public void setFunctioning(Boolean functioning) {
        this.functioning = functioning;
    }

    public Boolean getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(Boolean onOrder) {
        this.onOrder = onOrder;
    }

    public CityDto getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(CityDto currentCity) {
        this.currentCity = currentCity;
    }
}
