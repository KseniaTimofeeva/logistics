package com.tsystems.app.logistics.controller.manager;

import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.PathPointDto;
import com.tsystems.app.logistics.service.api.CityService;
import com.tsystems.app.logistics.service.api.OrderService;
import com.tsystems.app.logistics.service.api.PathPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by ksenia on 08.10.2017.
 */
@Controller
@RequestMapping({"/manager", "/manager/order"})
public class ManagerOrderController {

    private String typeOfCenterAttribute = "typeOfCenter";

    @Autowired
    private OrderService orderService;
    @Autowired
    private PathPointService pathPointService;
    @Autowired
    private CityService cityService;

    @RequestMapping
    public String getManagerOrder(Model model) {
        model.addAttribute(typeOfCenterAttribute, "manager/order.jsp");
        model.addAttribute("allOrders", orderService.getAllOrders());
        return "page";
    }

    @RequestMapping(value = "/{orderId}")
    public String getManagerSelectedOrder(@PathVariable(value = "orderId") Long orderId, Model model) {
        model.addAttribute(typeOfCenterAttribute, "manager/selected-order.jsp");
        model.addAttribute("orderInfo", orderService.getOrderInfoById(orderId));
        return "page";
    }

    @RequestMapping(value = {"/{orderId}/new-point", "/{orderId}/new-point/{pathPointId}"}, method = RequestMethod.GET)
    public String getManagerOrderNewPoint(@PathVariable(value = "orderId") Long orderId,
                                          @PathVariable(value = "pathPointId", required = false) Long pathPointId, Model model) {
        model.addAttribute(typeOfCenterAttribute, "manager/new-pathpoint.jsp");
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("orderInfo", orderService.getOrderById(orderId));
        if (pathPointId != null) {
            model.addAttribute("updatedPoint", pathPointService.getPathPointById(pathPointId));
        }
        return "page";
    }

    @RequestMapping(value = "/{orderId}/delete/{pathPointId}", method = RequestMethod.GET)
    public String deleteDriver(@PathVariable(value = "orderId") Long orderId,
                               @PathVariable(value = "pathPointId") Long pathPointId, Model model) {
        pathPointService.deletePathPoint(pathPointId);
        return "redirect:/manager/order/" + orderId;
    }

    @RequestMapping(value = "/{orderId}/new-point", method = RequestMethod.POST)
    public String addNewPoint(@Valid PathPointDto pointDto) {
        pathPointService.processPathPoint(pointDto);
        return "redirect:/manager/order/" + pointDto.getOrderId();
    }


    @RequestMapping(value = {"/new"}, method = RequestMethod.GET)
    public String getNewOrderForm(Model model) {
        model.addAttribute(typeOfCenterAttribute, "manager/new-order.jsp");
        return "page";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewOrder(@Valid OrderDto orderDto) {
        Long orderId = orderService.addNewOrder(orderDto);
        return "redirect:/manager/order/" + orderId;
    }

    @RequestMapping(value = "/{orderId}/choose-truck", method = RequestMethod.GET)
    public String chooseTruck(@PathVariable(value = "orderId") Long orderId, Model model) {

        return "page";
    }
}
