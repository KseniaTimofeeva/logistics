package com.tsystems.app.logistics.entity;

import com.tsystems.app.logistics.entity.enums.DriverAction;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by ksenia on 05.10.2017.
 */
@Entity
@Table(name = "time_tracks")
@Where(clause = "visible=true")
@NamedQueries({
        @NamedQuery(name = TimeTrack.GET_LAST_ACTION_BY_DRIVER_LOGIN,
                query = "select tt from TimeTrack tt where tt.user.login = :login and tt.order.id = :orderId order by tt.id desc"),
        @NamedQuery(name = TimeTrack.GET_TRACKS_FOR_ORDER,
                query = "select tt from TimeTrack tt where tt.order.id = :orderId"),
        @NamedQuery(name = TimeTrack.GET_TRACKS_IN_CURRENT_MONTH,
                query = "select tt from TimeTrack tt where tt.user.id = :driverId and tt.date >= :fisrtDayOfMonth and " +
                        "(tt.driverAction = 'START_DRIVING' or tt.driverAction = 'END_DRIVING' or " +
                        "tt.driverAction = 'START_LOAD_UNLOAD' or tt.driverAction = 'END_LOAD_UNLOAD') order by tt.date")
})
public class TimeTrack extends BaseEntity {

    public static final String GET_LAST_ACTION_BY_DRIVER_LOGIN = "TimeTrack.getLastActionByDriverLogin";
    public static final String GET_TRACKS_FOR_ORDER = "TimeTrack.getTracksForOrder";
    public static final String GET_TRACKS_IN_CURRENT_MONTH = "TimeTrack.getTracksInCurrentMonth";
    @ManyToOne
    private User user;

    @Column
    private Timestamp date;

    @Enumerated(EnumType.STRING)
    private DriverAction driverAction;
    @ManyToOne
    private Order order;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public DriverAction getDriverAction() {
        return driverAction;
    }

    public void setDriverAction(DriverAction driverAction) {
        this.driverAction = driverAction;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
