package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logisticscommon.CityDto;
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

    <T> T getCurrentOrderByDriverLogin(String login, Class<T> tClass);

    List<OrderInfoBoardDto> getOrdersInfo();

    List<CityDto> getRouteByOrderId(Long orderId);

    void addCityToRoute(Long orderId, List<Long> cityIdList);

    void removeCityFromRoute(Long orderId, Long cityId);

    void updateBoardUpdateOrder(Order order);

    void updateBoardUpdateOrder(String driverLogin);

    void closeOrder(Long orderId);

    boolean isAllPointsDoneByOrder(Order order);

    boolean isAllPointsDoneByOrderId(Long orderId);

}
