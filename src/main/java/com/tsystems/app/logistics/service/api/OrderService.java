package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.OrderInfoDto;

import java.util.List;

/**
 * Created by ksenia on 13.10.2017.
 */
public interface OrderService {

    List<OrderInfoDto> getAllOrders();

    Long addNewOrder(OrderDto orderDto);

    OrderInfoDto getOrderInfoById(Long id);

    OrderDto getOrderById(Long id);
}
