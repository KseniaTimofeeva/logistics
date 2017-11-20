package com.tsystems.app.logistics.dto;

import com.tsystems.app.logistics.entity.Cargo;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logisticscommon.CityDto;

/**
 * Created by ksenia on 14.10.2017.
 */
public class PathPointDto {

    private Long id;
    private Long orderId;
    private CargoDto cargo;
    private CityDto city;
    private Boolean loading;
    private Boolean done;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public CargoDto getCargo() {
        return cargo;
    }

    public void setCargo(CargoDto cargo) {
        this.cargo = cargo;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
