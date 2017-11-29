package com.tsystems.app.logistics.entity.enums;

/**
 * Created by ksenia on 05.10.2017.
 */
public enum DriverAction {
    START_WORKING_SHIFT(false, "Start order"),
    END_WORKING_SHIFT(false, "Finish order"),
    START_TRUCK_REPAIRING(true, "Truck is under repair"),
    START_DRIVING(true, "Driving"),
    START_SECOND(true, "Second driver"),
    START_LOAD_UNLOAD(true, "Loading - unloading"),
    START_RELAX(true, "Relaxation");

    private final boolean showActionToDriver;
    private final String viewName;

    DriverAction(boolean showActionToDriver, String viewName) {
        this.showActionToDriver = showActionToDriver;
        this.viewName = viewName;
    }

    public boolean isShowActionToDriver() {
        return showActionToDriver;
    }

    public String getViewName() {
        return viewName;
    }

}
