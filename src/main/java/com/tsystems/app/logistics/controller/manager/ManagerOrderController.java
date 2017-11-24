package com.tsystems.app.logistics.controller.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logistics.dto.PathPointDto;
import com.tsystems.app.logistics.dto.SuitableTruckDto;
import com.tsystems.app.logistics.service.api.CityService;
import com.tsystems.app.logistics.service.api.DriverService;
import com.tsystems.app.logistics.service.api.OrderService;
import com.tsystems.app.logistics.service.api.PathPointService;
import com.tsystems.app.logistics.service.api.TruckService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by ksenia on 08.10.2017.
 */
@Controller
@RequestMapping({"/manager", "/manager/order"})
public class ManagerOrderController {
    private static final Logger LOG = LogManager.getLogger(ManagerOrderController.class);

    private String typeOfCenterAttribute = "typeOfCenter";

    @Autowired
    private OrderService orderService;
    @Autowired
    private PathPointService pathPointService;
    @Autowired
    private CityService cityService;
    @Autowired
    private TruckService truckService;
    @Autowired
    private DriverService driverService;

    @RequestMapping
    public String getManagerOrder(Model model) {
        LOG.trace("GET /manager/order/");
        model.addAttribute(typeOfCenterAttribute, "manager/order.jsp");
        model.addAttribute("allOrders", orderService.getAllOrders());
        return "page";
    }

    @RequestMapping(value = "/{orderId}")
    public String getManagerSelectedOrder(@PathVariable(value = "orderId") Long orderId, Model model) {
        LOG.trace("GET /manager/order/{}", orderId);

        model.addAttribute(typeOfCenterAttribute, "manager/selected-order.jsp");

        OrderInfoDto orderInfo = orderService.getOrderInfoById(orderId);
        SuitableTruckDto suitableTrucks = truckService.getSuitableTruckByOrderId(orderId);
        LOG.debug("Number of suitable trucks for order fulfilling {}", suitableTrucks.getTrucks().size());
        model.addAttribute("cities", cityService.getAllCities());
        String json = null;
        if (orderInfo.getRoute() != null) {
            try {
                json = new ObjectMapper().writeValueAsString(orderInfo.getRoute());
            } catch (JsonProcessingException ignored) {
            }
        }
        model.addAttribute("routeAsString", json);
        model.addAttribute("hasCargoToUnload", pathPointService.hasCargoToUnload(orderId));
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("suitableTrucks", suitableTrucks);
        model.addAttribute("suitableDrivers", driverService.getSuitableDriversForOrder(orderId));

        return "page";
    }

    @RequestMapping(value = {"/{orderId}/new-point", "/{orderId}/new-point/{pathPointId}"}, method = RequestMethod.GET)
    public String getManagerOrderNewPoint(@PathVariable(value = "orderId") Long orderId,
                                          @PathVariable(value = "pathPointId", required = false) Long pathPointId, Model model) {
        LOG.trace("GET /manager/order/{}/new-point", orderId);
        model.addAttribute(typeOfCenterAttribute, "manager/new-pathpoint.jsp");
        model.addAttribute("cities", cityService.getAllCities());
        List<PathPointDto> pathPointsWithCargoToUnload = pathPointService.getPathPointsWithCargoToUnload(orderId);
        model.addAttribute("pointsWithCargoToUnload", pathPointsWithCargoToUnload);
        OrderDto orderById = orderService.getOrderById(orderId);
        model.addAttribute("orderInfo", orderById);

        LOG.debug("Order № {}: Number of points with cargo to unload {}", orderById.getNumber(), pathPointsWithCargoToUnload.size());
        if (pathPointId != null) {
            model.addAttribute("updatedPoint", pathPointService.getPathPointById(pathPointId));
        }
        return "page";
    }

    @RequestMapping(value = "/{orderId}/delete/{pathPointId}", method = RequestMethod.GET)
    public String deleteDriver(@PathVariable(value = "orderId") Long orderId,
                               @PathVariable(value = "pathPointId") Long pathPointId, Model model) {
        LOG.trace("GET /manager/order/{}/delete/{}", orderId, pathPointId);
        pathPointService.deletePathPoint(pathPointId);
        return "redirect:/manager/order/" + orderId;
    }

    @RequestMapping(value = "/{orderId}/new-point", method = RequestMethod.POST)
    public String addNewPoint(@PathVariable(value = "orderId") Long orderId,
                              @Valid PathPointDto pointDto) {
        LOG.trace("POST /manager/order/{}/new-point", pointDto.getOrderId());
        try {
            pathPointService.processPathPoint(pointDto);
        } catch (Exception e) {
            LOG.trace("New cargo exception. {}", e.getMessage());
            return "redirect:/manager/order/" + orderId + "/new-point?error";
        }
        return "redirect:/manager/order/" + pointDto.getOrderId();
    }


    @RequestMapping(value = {"/new"}, method = RequestMethod.GET)
    public String getNewOrderForm(Model model) {
        LOG.trace("GET /manager/order/new");
        model.addAttribute(typeOfCenterAttribute, "manager/new-order.jsp");
        return "page";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewOrder(@Valid OrderDto orderDto) {
        LOG.trace("POST /manager/order/new");
        Long orderId;
        try {
            orderId = orderService.addNewOrder(orderDto);
        } catch (Exception e) {
            LOG.trace("New order form exception. {}", e.getMessage());
            return "redirect:/manager/order/new?error";
        }
        return "redirect:/manager/order/" + orderId;
    }

    @RequestMapping(value = "/{orderId}/choose-truck", method = RequestMethod.POST)
    public String chooseTruck(@RequestParam Long truckId,
                              @PathVariable(value = "orderId") Long orderId) {
        LOG.trace("POST /manager/order/{}/choose-truck", orderId);
        orderService.setTruckForOrder(orderId, truckId);
        return "redirect:/manager/order/" + orderId;
    }

    @RequestMapping(value = "/{orderId}/choose-driver", method = RequestMethod.POST)
    public String chooseDriver(@RequestParam Long driverId,
                               @PathVariable(value = "orderId") Long orderId) {
        LOG.trace("POST /manager/order/{}/choose-driver", orderId);
        orderService.setDriverForOrder(orderId, driverId);
        return "redirect:/manager/order/" + orderId;
    }

    @RequestMapping(value = "/{orderId}/detach-driver/{driverId}", method = RequestMethod.GET)
    public String detachDriver(@PathVariable(value = "orderId") Long orderId,
                               @PathVariable(value = "driverId") Long driverId, Model model) {
        LOG.trace("GET /manager/order/{}/detach-driver/{}", orderId, driverId);
        orderService.detachDriver(orderId, driverId);
        return "redirect:/manager/order/" + orderId;
    }

}
