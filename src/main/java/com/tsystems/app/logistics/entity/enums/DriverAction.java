package com.tsystems.app.logistics.entity.enums;

import com.sun.org.apache.bcel.internal.generic.FALOAD;

/**
 * Created by ksenia on 05.10.2017.
 */
public enum DriverAction {
    START_WORKING_SHIFT(false, null),
    END_WORKING_SHIFT(false, null),
    START_DRIVING(true, "Driving"),
//    END_DRIVING(false, null),
    START_SECOND(true, "Second driver"),
//    END_SECOND(false, null),
    START_LOAD_UNLOAD(true, "Loading - unloading"),
//    END_LOAD_UNLOAD(false, null),
    START_RELAX(true, "Relaxation");
//    END_RELAX(false, null);

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

   /* public boolean isEnd() {
        if (this.equals(END_DRIVING) || this.equals(END_LOAD_UNLOAD)) {
            return true;
        }
        return false;
    }*/
}
