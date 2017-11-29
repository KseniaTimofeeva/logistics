package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logistics.entity.CityOfRoute;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logisticscommon.OrderInfoBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class OrderConverter {

    @Autowired
    private PathPointConverter pointConverter;
    @Autowired
    private CrewConverter crewConverter;
    @Autowired
    private CityConverter cityConverter;

    public OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setNumber(order.getNumber());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }

    public OrderInfoDto toOrderInfoDto(Order order) {
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        orderInfoDto.setId(order.getId());
        orderInfoDto.setNumber(order.getNumber());
        orderInfoDto.setStatus(order.getStatus());
        if (order.getPathPoints() != null) {
            orderInfoDto.setPathPoints(pointConverter.toPathPointDtoList(order.getPathPoints()));
        }
        if (order.getCrew() != null) {
            orderInfoDto.setCrew(crewConverter.toCrewDto(order.getCrew()));
        }
        if (order.getRoute() != null) {
            orderInfoDto.setRoute(cityConverter.routeToCityDtoList(order.getRoute()));
        }
        return orderInfoDto;
    }

    public List<OrderInfoDto> toOrderInfoDtoList(List<Order> orderList) {
        return orderList
                .stream()
                .map(order ->
                        toOrderInfoDto(order))
                .collect(Collectors.toList());
    }

    public OrderInfoBoardDto toOrderInfoBoardDto(Order order) {
        OrderInfoBoardDto dto = new OrderInfoBoardDto();
        dto.setId(order.getId());
        dto.setNumber(order.getNumber());
        List<CityOfRoute> route = order.getRoute();
        if (route != null && !route.isEmpty()) {
            dto.setStart(cityConverter.cityOfRouteToCityDto(route.get(0)));
            dto.setFinish(cityConverter.cityOfRouteToCityDto(route.get(route.size()-1)));
        }
        dto.setStatus(order.getStatus());
        if (order.getCrew() != null) {
            dto.setCrew(crewConverter.toCrewShortDto(order.getCrew()));
        }
        return dto;
    }

    public List<OrderInfoBoardDto> toOrderInfoBoardDtoList(List<Order> orders) {
        return orders
                .stream()
                .map(order ->
                        toOrderInfoBoardDto(order))
                .collect(Collectors.toList());
    }

}
