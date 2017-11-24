package com.tsystems.app.logistics.controller.rest;

import com.tsystems.app.logistics.service.api.TruckService;
import com.tsystems.app.logisticscommon.TruckFullDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ksenia on 11.11.2017.
 */
@RestController
@RequestMapping("/rest")
public class TruckRestController {

    @Autowired
    private TruckService truckService;

    @RequestMapping(value = "/trucks-info", method = RequestMethod.GET)
    public List<TruckFullDto> getTrucksFullInfo() {
        return truckService.getTrucksFullInfo();
    }
}
