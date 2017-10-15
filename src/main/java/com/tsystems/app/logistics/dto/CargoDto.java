package com.tsystems.app.logistics.dto;

import com.tsystems.app.logistics.entity.enums.CargoStatus;

/**
 * Created by ksenia on 14.10.2017.
 */
public class CargoDto {
    private Long id;
    private String number;
    private String name;
    private Float weight;
    private CargoStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public CargoStatus getStatus() {
        return status;
    }

    public void setStatus(CargoStatus status) {
        this.status = status;
    }
}
