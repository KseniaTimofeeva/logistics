package com.tsystems.app.logistics.controller.manager;

import com.tsystems.app.logistics.dto.DriverDto;
import com.tsystems.app.logistics.service.api.CityService;
import com.tsystems.app.logistics.service.api.DriverService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by ksenia on 10.10.2017.
 */
@Controller
@RequestMapping("/manager/driver")
public class ManagerDriverController {
    private static final Logger LOG = LogManager.getLogger(ManagerDriverController.class);

    private String typeOfCenterAttribute = "typeOfCenter";

    @Autowired
    private DriverService driverService;
    @Autowired
    private CityService cityService;

    @RequestMapping
    public String getManagerDriver(Model model) {
        LOG.trace("GET /manager/driver");
        model.addAttribute(typeOfCenterAttribute, "manager/driver.jsp");
        model.addAttribute("allDrivers", driverService.getAllDrivers());
        return "page";
    }

    @RequestMapping(value = {"/new", "/new/{driverId}"}, method = RequestMethod.GET)
    public String getNewDriverForm(@PathVariable(value = "driverId", required = false) Long driverId, Model model) {
        if (driverId == null) {
            LOG.trace("GET /manager/driver/new");
        } else {
            LOG.trace("GET /manager/driver/new/{}", driverId);
        }
        model.addAttribute(typeOfCenterAttribute, "manager/new-driver.jsp");
        if (driverId != null) {
            model.addAttribute("updatedDriver", driverService.getDriverById(driverId));
        }
        model.addAttribute("cities", cityService.getAllCities());
        return "page";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewDriver(@Valid DriverDto driverDto) {
        LOG.trace("POST /manager/driver/new");
        try {
            driverService.processDriver(driverDto);
        } catch (Exception e) {
            LOG.trace("New driver form exception. {}", e.getMessage());
            return "redirect:/manager/driver/new?error";
        }
        return "redirect:/manager/driver";
    }

    @RequestMapping(value = "/delete/{driverId}", method = RequestMethod.GET)
    public String deleteDriver(@PathVariable(value = "driverId") Long driverId) {
        LOG.trace("GET /manager/delete/{}", driverId);
        driverService.deleteDriver(driverId);
        return "redirect:/manager/driver";
    }
}
