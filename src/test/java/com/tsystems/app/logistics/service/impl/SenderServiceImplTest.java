package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.TestConfig;
import com.tsystems.app.logistics.dto.ChangeEvent;
import com.tsystems.app.logistics.dto.DriverDto;
import com.tsystems.app.logisticscommon.CityDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
public class SenderServiceImplTest {
    private static boolean setup;
    @Autowired
    private DriverServiceImpl driverService;
    @Autowired
    private SenderServiceImpl senderService;
    private static int calls;

    @Test
    public void typedSendCommitTest() {
        Mockito.verify(senderService, times(calls)).typedSend(Mockito.any(ChangeEvent.class));
    }

    @Before
    public void init() {
        if (setup) return;
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

        calls = Mockito.mockingDetails(senderService).getInvocations().size();

        setup = true;
    }

    @Test
    public void typedSendRollbackTest() {
        DriverDto driverDto = new DriverDto();
        driverDto.setFirstName("Petr");
        driverDto.setLastName("Ivanov");
        driverDto.setPersonalNumber("101");
        driverDto.setId(1L);
        CityDto cityDto = new CityDto();
        cityDto.setId(1L);
        driverDto.setCurrentCity(cityDto);
        driverDto.setOnOrder(false);
        driverDto.setLogin("login");
        driverDto.setPassword("password");

        try {
            driverService.addNewDriver(driverDto);
        } catch (Exception ignored) {
        }

        Mockito.verify(senderService, Mockito.times(calls)).typedSend(Mockito.any(ChangeEvent.class));
    }
}
