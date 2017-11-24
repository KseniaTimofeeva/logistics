package com.tsystems.app.logistics.controller.rest.manager;

import com.tsystems.app.logistics.service.api.OrderService;
import com.tsystems.app.logisticscommon.CityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ksenia on 22.11.2017.
 */
@RestController
@RequestMapping("/rest")
public class ManagerOrderRouteController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order-route/{orderId}/add-city", method = RequestMethod.POST)
    public List<CityDto> addCityToRoute(@PathVariable(value = "orderId") Long orderId,
                                        @RequestBody List<Long> cityIdList) {
        if (cityIdList != null && cityIdList.get(0) != null && cityIdList.get(0) != 0) {
            orderService.addCityToRoute(orderId, cityIdList);
        }
        return orderService.getRouteByOrderId(orderId);
    }

    @RequestMapping(value = "/order-route/{orderId}/remove-city", method = RequestMethod.POST)
    public List<CityDto> removeCityFromRoute(@PathVariable(value = "orderId") Long orderId,
                                             @RequestBody Long cityId) {
        if (cityId != null) {
            orderService.removeCityFromRoute(orderId, cityId);
        }
        return orderService.getRouteByOrderId(orderId);
    }
}
