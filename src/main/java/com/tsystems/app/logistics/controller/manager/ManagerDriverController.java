package com.tsystems.app.logistics.controller.manager;

import com.tsystems.app.logistics.dto.DriverDto;
import com.tsystems.app.logistics.service.api.DriverService;
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

    private String typeOfCenterAttribute = "typeOfCenter";

    @Autowired
    private DriverService driverService;

    @RequestMapping
    public String getManagerDriver(Model model) {
        model.addAttribute(typeOfCenterAttribute, "manager/driver.jsp");
        model.addAttribute("allDrivers", driverService.getAllDrivers());
        return "page";
    }

    @RequestMapping(value = {"/new", "/new/{driverId}"}, method = RequestMethod.GET)
    public String getNewDriverForm(@PathVariable(value = "driverId", required = false) Long driverId, Model model) {
        model.addAttribute(typeOfCenterAttribute, "manager/new-driver.jsp");
        if (driverId != null) {
            model.addAttribute("updatedDriver", driverService.getDriverById(driverId));
        }
        return "page";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewDriver(@Valid DriverDto driverDto) {
        driverService.processDriver(driverDto);
        return "redirect:/manager/driver";
    }

    @RequestMapping(value = "/delete/{driverId}", method = RequestMethod.GET)
    public String deleteDriver(@PathVariable(value = "driverId") Long driverId) {
        driverService.deleteDriver(driverId);
        return "redirect:/manager/driver";
    }
}
