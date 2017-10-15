package com.tsystems.app.logistics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by ksenia on 04.10.2017.
 */
@Entity
@Table(name = "cities")
@NamedQueries({
        @NamedQuery(name = City.GET_ALL_CITIES,
                query = "select c from City c where c.visible = true ORDER BY c.name")
})
public class City extends BaseEntity {

    public static final String GET_ALL_CITIES = "City.getAllCities";

    @Column
    private String name;
    @Column
    private Double latitude;
    @Column
    private Double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
