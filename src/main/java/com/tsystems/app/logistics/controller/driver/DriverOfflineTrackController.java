package com.tsystems.app.logistics.controller.driver;

import com.tsystems.app.logistics.service.api.DriverService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ksenia on 25.11.2017.
 */
@Controller
@RequestMapping("/driver/track-offline")
public class DriverOfflineTrackController {
    private static final Logger LOG = LogManager.getLogger(DriverOfflineTrackController.class);

    private String typeOfCenterAttribute = "typeOfCenter";

    @Autowired
    private DriverService driverService;

    @RequestMapping
    public String getDriverProfile(@AuthenticationPrincipal User user, Model model) {
        LOG.trace("GET /driver/track-offline {}", user.getUsername());
        model.addAttribute(typeOfCenterAttribute, "driver/driver-track-offline.jsp");
        return "page";
    }

    @RequestMapping(value = "/add-action", method = RequestMethod.POST)
    public String addDriverAction(@AuthenticationPrincipal User user, Model model) {
//        LOG.trace("POST /driver/track-offline/add-action {}", trackDto.getDriverAction().name());
//        trackService.addNewTimeTrack(user.getUsername(), trackDto);
        return "redirect:/driver/track-offline";
    }

}
