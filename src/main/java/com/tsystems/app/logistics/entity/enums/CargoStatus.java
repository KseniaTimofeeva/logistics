package com.tsystems.app.logistics.entity.enums;

/**
 * Created by ksenia on 03.10.2017.
 */
public enum CargoStatus {
    NEW("New"),
    SHIPPING("Shipping"),
    DELIVERED("Delivered");

    private final String viewName;

    CargoStatus(String viewName) {

        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
