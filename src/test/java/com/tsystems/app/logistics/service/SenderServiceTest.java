package com.tsystems.app.logistics.service;

import com.tsystems.app.logistics.TestConfig;
import com.tsystems.app.logistics.dto.ChangeEvent;
import com.tsystems.app.logistics.dto.DriverDto;
import com.tsystems.app.logistics.service.impl.DriverServiceImpl;
import com.tsystems.app.logistics.service.impl.SenderServiceImpl;
import com.tsystems.app.logisticscommon.CityDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.atLeastOnce;

/**
 * Created by ksenia on 28.11.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
public class SenderServiceTest {

    @Autowired
    private DriverServiceImpl driverService;
    @Autowired
    private SenderServiceImpl senderService;


    @Test
    public void typedSendCommitTest() {
        initAndAddNewDriver();

        Mockito.verify(senderService, atLeastOnce()).typedSend(Mockito.any(ChangeEvent.class));
    }

    private void initAndAddNewDriver() {
        DriverDto driverDto = new DriverDto();
        driverDto.setFirstName("Petr");
        driverDto.setLastName("Ivanov");
        driverDto.setPersonalNumber("101");
        CityDto cityDto = new CityDto();
        cityDto.setId(1L);
        driverDto.setCurrentCity(cityDto);
        driverDto.setOnOrder(false);
        driverDto.setLogin("login");
        driverDto.setPassword("password");

        driverService.addNewDriver(driverDto);
    }

   /* @Test
    public void typedSendRollbackTest() {
        DriverDto driverDto = new DriverDto();
        driverDto.setFirstName("Petr");
        driverDto.setLastName("Ivanov");
        driverDto.setPersonalNumber("101");
        CityDto cityDto = new CityDto();
        cityDto.setId(1L);
        driverDto.setCurrentCity(cityDto);
        driverDto.setOnOrder(false);
        driverDto.setLogin("login");
        driverDto.setPassword("password");

        driverService.addNewDriver(driverDto);

        Mockito.verify(senderService, atLeastOnce()).typedSend(Mockito.any(ChangeEvent.class));
    }*/
}
