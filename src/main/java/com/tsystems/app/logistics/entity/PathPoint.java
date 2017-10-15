package com.tsystems.app.logistics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by ksenia on 03.10.2017.
 */
@Entity
@Table(name = "path_points")
@NamedQueries({
        @NamedQuery(name = PathPoint.GET_ALL_POINTS_BY_ORDER_ID,
                query = "select pp from PathPoint pp where pp.order.id = :orderId and pp.visible = true")
})
public class PathPoint extends BaseEntity {

    public static final String GET_ALL_POINTS_BY_ORDER_ID = "PathPoint.getAllPointsByOrderId";

    @ManyToOne
    private Order order;

    @OneToOne
    private Cargo cargo;

    @OneToOne
    private City city;

    @Column
    private Boolean loading;

    @Column
    private Boolean done;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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

