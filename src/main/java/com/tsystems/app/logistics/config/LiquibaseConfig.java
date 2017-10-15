package com.tsystems.app.logistics.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by ksenia on 05.10.2017.
 */
@Configuration
public class LiquibaseConfig {

   /* @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/liquibase/db-changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }*/
}
