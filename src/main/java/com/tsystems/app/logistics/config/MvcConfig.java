package com.tsystems.app.logistics.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by ksenia on 03.10.2017.
 */
@EnableWebMvc   //<mvc:annotation-driven>
@Configuration
@ComponentScan(basePackages = "com.tsystems.app.logistics")
public class MvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger LOG = LogManager.getLogger(MvcConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    public ViewResolver contentNegotiatingViewResolver() {
        ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
        contentNegotiationManager.addMediaType("json", MediaType.APPLICATION_JSON);

        ContentNegotiatingViewResolver contentViewResolver = new ContentNegotiatingViewResolver();
        contentViewResolver.setContentNegotiationManager(contentNegotiationManager.getObject());

        contentViewResolver.setDefaultViews(Collections.singletonList(jsonView()));
        contentViewResolver.setViewResolvers(Collections.singletonList(internalResourceViewResolver()));

        return contentViewResolver;
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

    @Bean
    public ConnectionFactory jmsConnectionFactory() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:jboss/DefaultJMSConnectionFactory");
        jndiObjectFactoryBean.setLookupOnStartup(true);
        jndiObjectFactoryBean.setProxyInterface(ConnectionFactory.class);
        jndiObjectFactoryBean.afterPropertiesSet();
        return (ConnectionFactory) jndiObjectFactoryBean.getObject();
    }

    @Bean
    public Queue requestsQueue() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:/jms/queue/logisticsQueue");
        return (Queue) jndiObjectFactoryBean.getObject();
    }

    @Bean
    public JmsTemplate jmsOperations() throws NamingException {
        final JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory());
        jmsTemplate.setDefaultDestinationName(/*requestsQueue()*/"java:/jms/queue/logisticsQueue");
        return jmsTemplate;
    }
}

