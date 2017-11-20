package com.tsystems.app.logistics.entity;

import com.tsystems.app.logisticscommon.enums.OrderStatus;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by ksenia on 04.10.2017.
 */
@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = Order.GET_ALL_ORDERS,
                query = "select o from Order o where o.visible = true"),
        @NamedQuery(name = Order.GET_CURRENT_ORDER_BY_DRIVER_LOGIN,
                query = "select distinct o from Order o join o.crew.users u where " +
                        "u.login = :login and (o.status = 'NEW' or o.status = 'IN_PROCESS')"),
        @NamedQuery(name = Order.VALIDATE_NEW_ORDER,
                query = "select o from Order o where o.number = :number")
})
@Where(clause = "visible=true")
public class Order extends BaseEntity {

    public static final String GET_ALL_ORDERS = "Order.getAllOrders";
    public static final String GET_CURRENT_ORDER_BY_DRIVER_LOGIN = "Order.getCurrentOrderByDriverLogin";
    public static final String VALIDATE_NEW_ORDER = "Order.validateNewOrder";

    @Column(unique = true)
    private String number;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    @OrderBy("id")
    private List<PathPoint> pathPoints;

    @OneToOne
    private Crew crew;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<PathPoint> getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(List<PathPoint> pathPoints) {
        this.pathPoints = pathPoints;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }
}
