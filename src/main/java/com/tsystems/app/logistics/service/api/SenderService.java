package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.ChangeEvent;

/**
 * Created by ksenia on 02.11.2017.
 */
public interface SenderService {

    void typedSend (ChangeEvent changeEvent);
}
