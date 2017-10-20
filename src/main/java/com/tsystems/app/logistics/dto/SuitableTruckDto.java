package com.tsystems.app.logistics.dto;

import java.util.List;

/**
 * Created by ksenia on 15.10.2017.
 */
public class SuitableTruckDto {

    private List<TruckDto> trucks;
    private Boolean isCurrentTruckSuitable;

    public List<TruckDto> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<TruckDto> trucks) {
        this.trucks = trucks;
    }

    public Boolean getIsCurrentTruckSuitable() {
        return isCurrentTruckSuitable;
    }

    public void setIsCurrentTruckSuitable(Boolean isCurrentTruckSuitable) {
        this.isCurrentTruckSuitable = isCurrentTruckSuitable;
    }
}
