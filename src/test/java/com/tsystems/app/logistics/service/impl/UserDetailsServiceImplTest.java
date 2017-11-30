package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.DBUtils;
import com.tsystems.app.logistics.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
public class UserDetailsServiceImplTest {
    private final String USERNAME_NON_EXISTS = "manager_non_exists";
    private final String MANAGER = "manager";
    private final String M_PASSWORD = "manager";

    @Autowired
    private DBUtils dbUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void init() {
        dbUtils.createStubDataBase();
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameFailTest() {
        userDetailsService.loadUserByUsername(USERNAME_NON_EXISTS);
    }

    @Test
    public void loadUserByUsernameTest() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(MANAGER);
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(userDetails.getPassword(), M_PASSWORD);
    }
}