package com.tsystems.app.logistics.entity;

import com.tsystems.app.logistics.entity.enums.DriverAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by ksenia on 05.10.2017.
 */
@Entity
@Table(name = "time_tracks")
public class TimeTrack extends BaseEntity {

    @ManyToOne
    private User user;

    @Column
    private Timestamp date;

    @Enumerated(EnumType.STRING)
    private DriverAction driverAction;

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
}
