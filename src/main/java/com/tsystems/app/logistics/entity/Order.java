package com.tsystems.app.logistics.entity;

import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by ksenia on 04.10.2017.
 */
@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = Order.GET_ALL_ORDERS,
                query = "select o from Order o where o.visible = true")
})
public class Order extends BaseEntity {

    public static final String GET_ALL_ORDERS = "Order.getAllOrders";

    @Column(unique = true)
    private String number;
    @Column
    private Boolean finished = false;

    @OneToMany(mappedBy = "order")
    private List<PathPoint> pathPoints;

    @OneToOne
    private Crew crew;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
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
