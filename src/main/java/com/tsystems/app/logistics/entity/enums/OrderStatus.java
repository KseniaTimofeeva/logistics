package com.tsystems.app.logistics.entity.enums;

/**
 * Created by ksenia on 16.10.2017.
 */
public enum OrderStatus {
    NEW("New"),
    IN_PROCESS("In process"),
    FINISHED("Finished");

    private final String viewName;

    OrderStatus(String viewName) {

        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
