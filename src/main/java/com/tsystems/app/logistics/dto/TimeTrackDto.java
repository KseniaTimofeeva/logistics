package com.tsystems.app.logistics.dto;

import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.DriverAction;

import java.sql.Timestamp;

/**
 * Created by ksenia on 21.10.2017.
 */
public class TimeTrackDto {

    private DriverShortDto driver;
    private Timestamp date;
    private DriverAction driverAction;
    private DriverAction currentAction;

    public DriverShortDto getDriver() {
        return driver;
    }

    public void setDriver(DriverShortDto driver) {
        this.driver = driver;
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

    public DriverAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(DriverAction currentAction) {
        this.currentAction = currentAction;
    }
}
