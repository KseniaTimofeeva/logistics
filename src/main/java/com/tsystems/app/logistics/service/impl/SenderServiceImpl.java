package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.service.api.SenderService;
import com.tsystems.app.logisticscommon.MessageDto;
import com.tsystems.app.logisticscommon.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Created by ksenia on 02.11.2017.
 */
@Service
public class SenderServiceImpl implements SenderService {

    private static final Logger LOG = LogManager.getLogger(SenderServiceImpl.class);

    @Autowired
    private Queue queue;
    @Autowired
    private JmsTemplate jmsTemplate;

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void simpleSend() {
        this.jmsTemplate.send("logisticsQueue", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello queue");
            }
        });
    }

    @Override
    public void typedSend(MessageType type, Object changes) {
        this.jmsTemplate.send("logisticsQueue", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MessageDto object = new MessageDto(type, changes);
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setObject(object);
                return objectMessage;
            }
        });
    }
}
