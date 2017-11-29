package com.tsystems.app.logistics.controller.manager;

import com.tsystems.app.logistics.dto.TruckDto;
import com.tsystems.app.logistics.service.api.CityService;
import com.tsystems.app.logistics.service.api.TruckService;
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
@RequestMapping("/manager/truck")
public class ManagerTruckController {
    private static final Logger LOG = LogManager.getLogger(ManagerTruckController.class);

    private String typeOfCenterAttribute = "typeOfCenter";

    @Autowired
    private TruckService truckService;
    @Autowired
    private CityService cityService;

    @RequestMapping
    public String getManagerTruck(Model model) {
        LOG.trace("GET /manager/truck");
        model.addAttribute(typeOfCenterAttribute, "manager/truck.jsp");
        model.addAttribute("allTrucks", truckService.getAllTrucks());
        return "page";
    }

    @RequestMapping(value = {"/new", "/new/{truckId}"}, method = RequestMethod.GET)
    public String getNewTruckForm(@PathVariable(value = "truckId", required = false) Long truckId, Model model) {
        LOG.trace("GET /manager/truck/new");
        model.addAttribute(typeOfCenterAttribute, "manager/new-truck.jsp");
        if (truckId != null) {
            model.addAttribute("updatedTruck", truckService.getTruckById(truckId));
        }
        model.addAttribute("cities", cityService.getAllCities());
        return "page";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewTruck(@Valid TruckDto truckDto) {
        LOG.trace("POST /manager/truck/new");
        try {
            truckService.processTruck(truckDto);
        } catch (Exception e) {
            LOG.trace("New truck form exception. {}", e.getMessage());
            if (truckDto.getId() != null) {
                return "redirect:/manager/truck/new/" + truckDto.getId() + "?error="+e.getMessage();
            }
            return "redirect:/manager/truck/new?error="+e.getMessage();
        }
        return "redirect:/manager/truck";
    }

    @RequestMapping(value = "/delete/{truckId}", method = RequestMethod.GET)
    public String deleteTruck(@PathVariable(value = "truckId") Long truckId) {
        LOG.trace("GET /manager/truck/delete/{}", truckId);
        truckService.deleteTruck(truckId);
        return "redirect:/manager/truck";
    }
}
