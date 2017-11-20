package com.tsystems.app.logistics.config;

import com.tsystems.app.logistics.handler.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by ksenia on 08.10.2017.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/driver/**").access("hasRole('ROLE_DRIVER')")
                .antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')")
                .antMatchers("/rest/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .failureForwardUrl("/login?error")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .usernameParameter("login")
                .passwordParameter("password")
                .loginPage("/login")
                .permitAll();

        http.logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }


}
