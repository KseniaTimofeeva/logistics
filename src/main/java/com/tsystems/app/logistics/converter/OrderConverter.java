package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logistics.entity.Order;
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

    public OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setNumber(order.getNumber());
        orderDto.setFinished(order.getFinished());
        return orderDto;
    }

    public OrderInfoDto toOrderInfoDto(Order order) {
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        orderInfoDto.setId(order.getId());
        orderInfoDto.setNumber(order.getNumber());
        orderInfoDto.setFinished(order.getFinished());
        if (order.getPathPoints() != null) {
            orderInfoDto.setPathPoints(pointConverter.toPathPointDtoList(order.getPathPoints()));
        }
        if (order.getCrew() != null) {
            orderInfoDto.setCrew(crewConverter.toCrewDto(order.getCrew()));
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
}
