package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logisticscommon.MessageType;

/**
 * Created by ksenia on 02.11.2017.
 */
public interface SenderService {

    void simpleSend();

    void typedSend (MessageType type, Object change);
}
