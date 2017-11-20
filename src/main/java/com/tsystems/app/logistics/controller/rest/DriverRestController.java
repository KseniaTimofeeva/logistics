package com.tsystems.app.logistics.controller.rest;

import com.tsystems.app.logistics.service.api.DriverService;
import com.tsystems.app.logisticscommon.DriverInfoBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ksenia on 11.11.2017.
 */
@RestController
@RequestMapping("/rest")
public class DriverRestController {

    @Autowired
    private DriverService driverService;

    @RequestMapping("/drivers-info")
    public List<DriverInfoBoardDto> getDriversInfo() {
        return driverService.getDriversInfo();
    }
}
