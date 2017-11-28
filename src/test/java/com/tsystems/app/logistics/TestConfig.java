package com.tsystems.app.logistics;

import com.tsystems.app.logistics.config.MvcConfig;
import com.tsystems.app.logistics.service.api.SenderService;
import com.tsystems.app.logistics.service.impl.SenderServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.naming.NamingException;

/**
 * Created by ksenia on 28.11.2017.
 */
@Configuration
@Import(MvcConfig.class)
public class TestConfig {

    @Bean
    @Primary
    public ConnectionFactory jmsConnectionFactory() throws NamingException {
        return null;
    }

    @Bean
    @Primary
    public JmsTemplate jmsOperations() throws NamingException {
        return Mockito.mock(JmsTemplate.class);
    }

    @Bean
    @Primary
    SenderService senderService() {
        return Mockito.mock(SenderServiceImpl.class);
    }

}
