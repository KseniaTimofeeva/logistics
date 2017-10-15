package com.tsystems.app.logistics.entity;

import com.tsystems.app.logistics.entity.enums.CargoStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by ksenia on 04.10.2017.
 */
@Entity
@Table(name = "cargo")
public class Cargo extends BaseEntity {

    @Column(unique = true)
    private String number;

    @Column
    private String name;

    @Column
    private Float weight;

    @Column
    @Enumerated(EnumType.STRING)
    private CargoStatus status;

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
