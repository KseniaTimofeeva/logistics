package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.OrderConverter;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ksenia on 13.10.2017.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        orderDao.setEntityClass(Order.class);
    }

    @Override
    public List<OrderInfoDto> getAllOrders() {
        List<Order> allOrders = orderDao.getAllOrders();
        return orderConverter.toOrderInfoDtoList(allOrders);
    }

    @Override
    public Long addNewOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setNumber(orderDto.getNumber());
        order = orderDao.create(order);
        return order.getId();
    }

    @Override
    public OrderInfoDto getOrderInfoById(Long id) {
        Order order = orderDao.findOneById(id);
        return orderConverter.toOrderInfoDto(order);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderDao.findOneById(id);
        return orderConverter.toOrderDto(order);
    }
}
