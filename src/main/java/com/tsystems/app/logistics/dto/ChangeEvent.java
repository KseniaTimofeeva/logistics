package com.tsystems.app.logistics.dto;

import com.tsystems.app.logisticscommon.MessageType;

/**
 * Created by ksenia on 26.11.2017.
 */
public class ChangeEvent {

    private MessageType type;
    private Boolean isDriverGeneralInfo;
    private Object changes;

    public ChangeEvent(MessageType type, Boolean isDriverGeneralInfo, Object changes) {
        this.type = type;
        this.isDriverGeneralInfo = isDriverGeneralInfo;
        this.changes = changes;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Object getChanges() {
        return changes;
    }

    public void setChanges(Object changes) {
        this.changes = changes;
    }

    public Boolean getDriverGeneralInfo() {
        return isDriverGeneralInfo;
    }

    public void setDriverGeneralInfo(Boolean driverGeneralInfo) {
        isDriverGeneralInfo = driverGeneralInfo;
    }
}
