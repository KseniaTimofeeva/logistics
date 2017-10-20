package com.tsystems.app.logistics.controller.driver;

import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logistics.service.api.OrderService;
import com.tsystems.app.logistics.service.api.PathPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping
    public String getDriverOrder(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(typeOfCenterAttribute, "driver/driver-order.jsp");
        OrderInfoDto currentOrder = orderService.getCurrentOrderByDriverLogin(user.getUsername());
        if (currentOrder != null) {
            model.addAttribute("currentOrder", currentOrder);
//            model.addAttribute("lastAction", )
        }

        return "page";
    }

    @RequestMapping("/close-point/{pathPointId}")
    public String closePathPoint(@PathVariable(value = "pathPointId") Long pathPointId, Model model) {
        pointService.closePathPoint(pathPointId);
        return "redirect:/driver/order";
    }

    @RequestMapping("/shift-start")
    public String startDriverWorkingShift(@AuthenticationPrincipal User user, Model model) {

        return "redirect:/driver/order";
    }

    @RequestMapping("/shift-stop")
    public String stopDriverWorkingShift(@AuthenticationPrincipal User user, Model model) {

        return "redirect:/driver/order";
    }
}
