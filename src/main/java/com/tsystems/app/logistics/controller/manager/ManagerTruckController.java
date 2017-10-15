package com.tsystems.app.logistics.controller.manager;

import com.tsystems.app.logistics.dto.TruckDto;
import com.tsystems.app.logistics.service.api.TruckService;
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

    private String typeOfCenterAttribute = "typeOfCenter";

    @Autowired
    private TruckService truckService;

    @RequestMapping
    public String getManagerTruck(Model model) {
        model.addAttribute(typeOfCenterAttribute, "manager/truck.jsp");
        model.addAttribute("allTrucks", truckService.getAllTrucks());
        return "page";
    }

    @RequestMapping(value = {"/new", "/new/{truckId}"}, method = RequestMethod.GET)
    public String getNewTruckForm(@PathVariable(value = "truckId", required = false) Long truckId, Model model) {
        model.addAttribute(typeOfCenterAttribute, "manager/new-truck.jsp");
        if (truckId != null) {
            model.addAttribute("updatedTruck", truckService.getTruckById(truckId));
        }
        return "page";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewTruck(@Valid TruckDto truckDto) {
        truckService.processTruck(truckDto);
        return "redirect:/manager/truck";
    }

    @RequestMapping(value = "/delete/{truckId}", method = RequestMethod.GET)
    public String deleteTruck(@PathVariable(value = "truckId") Long truckId) {
        truckService.deleteTruck(truckId);
        return "redirect:/manager/truck";
    }
}
