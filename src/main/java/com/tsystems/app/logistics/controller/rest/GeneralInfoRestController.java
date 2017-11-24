package com.tsystems.app.logistics.controller.rest;

import com.tsystems.app.logistics.service.api.GeneralInfoService;
import com.tsystems.app.logisticscommon.GeneralInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ksenia on 11.11.2017.
 */
@RestController
@RequestMapping("/rest")
public class GeneralInfoRestController {

    @Autowired
    private GeneralInfoService generalInfoService;

    @RequestMapping(value = "/general-info", method = RequestMethod.GET)
    public GeneralInfoDto generalInfo() {
        return generalInfoService.getGeneralInfo();
    }
}
