package com.tsystems.app.logistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resolver = new ResourceBundleMessageSource();
        resolver.setBasename("messages");
//        resolver.setUseCodeAsDefaultMessage(true);
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    @Bean
    public LocaleResolver sessionLocaleResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("en", "US"));
        return resolver;
    }
}