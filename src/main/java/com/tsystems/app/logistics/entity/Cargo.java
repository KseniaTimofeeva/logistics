package com.tsystems.app.logistics.entity;

import com.tsystems.app.logistics.entity.enums.CargoStatus;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by ksenia on 04.10.2017.
 */
@Entity
@Table(name = "cargo")
@Where(clause = "visible=true")
@NamedQueries({
        @NamedQuery(name = Cargo.NEW_CARGO_VALIDATE,
                query = "select c from Cargo c where c.number = :number")
})
public class Cargo extends BaseEntity {

    public static final String GET_CARGO_TO_UNLOAD = "Cargo.getCargoToUnload";
    public static final String NEW_CARGO_VALIDATE = "Cargo.newCargoValidate";

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
