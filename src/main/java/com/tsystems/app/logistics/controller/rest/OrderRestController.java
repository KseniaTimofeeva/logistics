package com.tsystems.app.logistics.controller.rest;

import com.tsystems.app.logistics.service.api.OrderService;
import com.tsystems.app.logisticscommon.OrderInfoBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ksenia on 11.11.2017.
 */
@RestController
@RequestMapping("/rest")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/orders-info")
    public List<OrderInfoBoardDto> getOrdersInfo() {
        return orderService.getOrdersInfo();
    }

}
