package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logisticscommon.OrderInfoBoardDto;

import java.util.List;

/**
 * Created by ksenia on 13.10.2017.
 */
public interface OrderService {

    List<OrderInfoDto> getAllOrders();

    Long addNewOrder(OrderDto orderDto);

    OrderInfoDto getOrderInfoById(Long id);

    OrderDto getOrderById(Long id);

    void setTruckForOrder(Long orderId, Long truckId);

    void setDriverForOrder(Long orderId, Long driverId);

    void detachDriver(Long orderId, Long driverId);

    OrderInfoDto getCurrentOrderByDriverLogin(String login);

    List<OrderInfoBoardDto> getOrdersInfo();
}
