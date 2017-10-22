package com.tsystems.app.logistics.controller.driver;

import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logistics.dto.TimeTrackDto;
import com.tsystems.app.logistics.entity.enums.DriverAction;
import com.tsystems.app.logistics.service.api.OrderService;
import com.tsystems.app.logistics.service.api.PathPointService;
import com.tsystems.app.logistics.service.api.TimeTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by ksenia on 18.10.2017.
 */
@Controller
@RequestMapping({"/driver", "/driver/order"})
public class DriverOrderController {

    private String typeOfCenterAttribute = "typeOfCenter";

    @Autowired
    private OrderService orderService;
    @Autowired
    private PathPointService pointService;
    @Autowired
    private TimeTrackService trackService;

    @RequestMapping
    public String getDriverOrder(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(typeOfCenterAttribute, "driver/driver-order.jsp");
        OrderInfoDto currentOrder = orderService.getCurrentOrderByDriverLogin(user.getUsername());
        if (currentOrder != null) {
            model.addAttribute("currentOrder", currentOrder);
            model.addAttribute("lastAction", trackService.getLastActionForOrder(user.getUsername(), currentOrder.getId()));
        }

        return "page";
    }

    @RequestMapping(value = "/close-point/{pathPointId}", method = RequestMethod.GET)
    public String closePathPoint(@PathVariable(value = "pathPointId") Long pathPointId, Model model) {
        pointService.closePathPoint(pathPointId);
        return "redirect:/driver/order";
    }

    @RequestMapping(value = "/add-action", method = RequestMethod.POST)
    public String addDriverAction(@AuthenticationPrincipal User user, @Valid TimeTrackDto trackDto, Model model) {
        trackService.addNewTimeTrack(user.getUsername(), trackDto);
        return "redirect:/driver/order";
    }
}
