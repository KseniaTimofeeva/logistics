package com.tsystems.app.logistics.entity;

import org.hibernate.annotations.Where;

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
                query = "select pp from PathPoint pp where pp.order.id = :orderId and pp.visible = true"),
        @NamedQuery(name = PathPoint.GET_POINTS_WITH_CARGO_TO_UNLOAD,
                query = "select p1 from PathPoint p1 where p1.loading = true and p1.order.id = :orderId and not exists " +
                        "(select p2 from PathPoint p2 where p1.cargo.id = p2.cargo.id and p2.loading = false and p2.order.id = :orderId)")
})
@Where(clause = "visible=true")
public class PathPoint extends BaseEntity {

    public static final String GET_ALL_POINTS_BY_ORDER_ID = "PathPoint.getAllPointsByOrderId";
    public static final String GET_POINTS_WITH_CARGO_TO_UNLOAD = "PathPoint.getPointsWithCargoToUnload";

    @ManyToOne
    private Order order;

    @ManyToOne
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

