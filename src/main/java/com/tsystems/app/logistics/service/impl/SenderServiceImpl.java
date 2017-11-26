package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.dto.ChangeEvent;
import com.tsystems.app.logistics.service.api.GeneralInfoService;
import com.tsystems.app.logistics.service.api.SenderService;
import com.tsystems.app.logisticscommon.GeneralInfoDto;
import com.tsystems.app.logisticscommon.MessageDto;
import com.tsystems.app.logisticscommon.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Created by ksenia on 02.11.2017.
 */
@Component
public class SenderServiceImpl implements SenderService {

    private static final Logger LOG = LogManager.getLogger(SenderServiceImpl.class);

    @Autowired
    private Queue queue;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private GeneralInfoService generalInfoService;

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    @Override
    @TransactionalEventListener
    public void typedSend(ChangeEvent changeEvent) {
        LOG.trace("Send changes after commit: {}", changeEvent.getType());

        if (changeEvent.getType().equals(MessageType.GENERAL)) {
            GeneralInfoDto generalInfoDto = new GeneralInfoDto();
            if (changeEvent.getDriverGeneralInfo()) {
                generalInfoDto = generalInfoService.setDriverInfo(generalInfoDto);
            } else {
                generalInfoDto = generalInfoService.setTruckInfo(generalInfoDto);
            }
            changeEvent.setChanges(generalInfoDto);
        }
        this.jmsTemplate.send("logisticsQueue", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MessageDto object = new MessageDto(changeEvent.getType(), changeEvent.getChanges());
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setObject(object);
                return objectMessage;
            }
        });
    }
}
