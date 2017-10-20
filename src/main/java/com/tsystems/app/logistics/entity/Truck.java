package com.tsystems.app.logistics.entity;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by ksenia on 03.10.2017.
 */
@Entity
@Table(name = "trucks")
@NamedQueries({
        @NamedQuery(name = Truck.GET_ALL_TRUCKS,
                query = "select t from Truck t where t.visible = true"),
        @NamedQuery(name = Truck.GET_SUITABLE_TRUCKS,
                query = "select t from Truck t where t.visible = true and t.functioning = true and t.onOrder = false and t.capacity >= :maxTotalWeight")
})
@Where(clause = "visible=true")
public class Truck extends BaseEntity {

    public static final String GET_ALL_TRUCKS = "Truck.getAllTrucks";
    public static final String GET_SUITABLE_TRUCKS = "Truck.getSuitableTrucks";

    @Column(name = "number_plate", unique = true)
    private String numberPlate;

    @Column(name = "working_shift")
    private Float workingShift;
    @Column
    private Float capacity;
    @Column
    private Boolean functioning;
    @Column(name = "on_order")
    private Boolean onOrder;

    @OneToMany(mappedBy = "truck")
    private List<Crew> crews;
    @OneToOne
    private City currentCity;


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

    public List<Crew> getCrews() {
        return crews;
    }

    public void setCrews(List<Crew> crews) {
        this.crews = crews;
    }

    public Boolean getOnOrder() {
        return onOrder;
    }

    public void setOnOrder(Boolean onOrder) {
        this.onOrder = onOrder;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }
}
