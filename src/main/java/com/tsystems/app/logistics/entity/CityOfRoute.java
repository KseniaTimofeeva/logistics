package com.tsystems.app.logistics.entity;

import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by ksenia on 23.11.2017.
 */
@Entity
@Table(name = "routes")
@Where(clause = "visible=true")
public class CityOfRoute extends BaseEntity {

    @OneToOne
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
