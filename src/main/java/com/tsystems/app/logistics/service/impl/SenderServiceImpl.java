package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.dto.ChangeEvent;
import com.tsystems.app.logistics.service.api.GeneralInfoService;
import com.tsystems.app.logistics.service.api.OrderService;
import com.tsystems.app.logistics.service.api.SenderService;
import com.tsystems.app.logisticscommon.GeneralInfoDto;
import com.tsystems.app.logisticscommon.MessageDto;
import com.tsystems.app.logisticscommon.MessageType;
import com.tsystems.app.logisticscommon.OrderInfoBoardDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * Created by ksenia on 02.11.2017.
 */
@Component
public class SenderServiceImpl implements SenderService {

    private static final Logger LOG = LogManager.getLogger(SenderServiceImpl.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private GeneralInfoService generalInfoService;
    @Autowired
    private OrderService orderService;
    @Value("${jms.queue.shortName}")
    private String queueName;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    @Override
    @TransactionalEventListener
    public void typedSend(ChangeEvent changeEvent) {
        LOG.trace("Send changes after commit: {}", changeEvent.getType());
        switch (changeEvent.getType()) {
            case GENERAL:
                GeneralInfoDto generalInfoDto = new GeneralInfoDto();
                if (changeEvent.getDriverGeneralInfo() == null) {
                    generalInfoDto = generalInfoService.setDriverInfo(generalInfoDto);
                    generalInfoDto = generalInfoService.setTruckInfo(generalInfoDto);
                } else {
                    if (changeEvent.getDriverGeneralInfo()) {
                        generalInfoDto = generalInfoService.setDriverInfo(generalInfoDto);
                    }
                    if (!changeEvent.getDriverGeneralInfo()){
                        generalInfoDto = generalInfoService.setTruckInfo(generalInfoDto);
                    }
                }

                changeEvent.setChanges(generalInfoDto);
                break;
            case UPDATE_ORDER_BY_DRIVER_LOGIN:
                changeEvent.setType(MessageType.UPDATE_ORDER);
                changeEvent.setChanges(orderService.getCurrentOrderByDriverLogin((String) changeEvent.getChanges(), OrderInfoBoardDto.class));
                break;
        }

        this.jmsTemplate.send(queueName, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MessageDto object = new MessageDto(changeEvent.getType(), changeEvent.getChanges());
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setObject(object);
                return objectMessage;
            }
        });
    }
}
