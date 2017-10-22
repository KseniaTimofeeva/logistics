package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.OrderConverter;
import com.tsystems.app.logistics.dao.impl.CrewDao;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dao.impl.TruckDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logistics.dto.OrderInfoDto;
import com.tsystems.app.logistics.entity.Crew;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.Truck;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.OrderStatus;
import com.tsystems.app.logistics.service.api.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ksenia on 13.10.2017.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LogManager.getLogger(OrderServiceImpl.class);

    private OrderDao orderDao;
    private TruckDao truckDao;
    private CrewDao crewDao;
    private UserDao userDao;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        orderDao.setEntityClass(Order.class);
    }

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
        truckDao.setEntityClass(Truck.class);
    }

    @Autowired
    public void setCrewDao(CrewDao crewDao) {
        this.crewDao = crewDao;
        crewDao.setEntityClass(Crew.class);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        userDao.setEntityClass(User.class);
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
        order.setStatus(OrderStatus.NEW);
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

    @Override
    public void setTruckForOrder(Long orderId, Long newTruckId) {

        Truck newTruck = truckDao.findOneById(newTruckId);
        newTruck.setOnOrder(true);
        newTruck = truckDao.update(newTruck);

        Order order = orderDao.findOneById(orderId);
        Crew orderCrew = order.getCrew();
        if (orderCrew == null) {
            orderCrew = new Crew();
            orderCrew.setTruck(newTruck);
            orderCrew.setOrder(order);
            orderCrew = crewDao.create(orderCrew);
        } else {
            Truck oldTruck = orderCrew.getTruck();
            if (oldTruck != null) {
                oldTruck.setOnOrder(false);
                truckDao.update(oldTruck);
            }
            orderCrew.setTruck(newTruck);
            orderCrew = crewDao.update(orderCrew);
        }
        order.setCrew(orderCrew);
        orderDao.update(order);
    }

    @Override
    public void setDriverForOrder(Long orderId, Long newDriverId) {
        Order order = orderDao.findOneById(orderId);

        User newDriver = userDao.findOneById(newDriverId);
        newDriver.setOnOrder(true);
        newDriver = userDao.update(newDriver);

        Crew orderCrew = order.getCrew();
        List<User> currentDrivers;
        if (orderCrew == null || orderCrew.getUsers() == null) {
            if (orderCrew == null) {
                orderCrew = new Crew();
            }
            currentDrivers = new ArrayList<>();
            currentDrivers.add(newDriver);
            orderCrew.setUsers(currentDrivers);
            orderCrew.setOrder(order);
            orderCrew = crewDao.create(orderCrew);
        } else {
            currentDrivers = orderCrew.getUsers();
            currentDrivers.add(newDriver);
            orderCrew = crewDao.update(orderCrew);
        }
        order.setCrew(orderCrew);
        orderDao.update(order);
    }

    /**
     * Delete driver from specified order.
     * Set driver status to 'vacant'
     * @param orderId id of the order
     * @param driverId driver id who would be deleted from specified otder
     */
    @Override
    public void detachDriver(Long orderId, Long driverId) {
        User driver = userDao.findOneById(driverId);
        driver.setOnOrder(false);
        driver = userDao.update(driver);

        Order order = orderDao.findOneById(orderId);
        Crew crew = order.getCrew();
        List<User> currentDrivers = crew.getUsers();
        currentDrivers.remove(driver);
        crew = crewDao.update(crew);
        order.setCrew(crew);
        orderDao.update(order);
    }

    @Override
    public OrderInfoDto getCurrentOrderByDriverLogin(String login) {
        List<Order> orders = orderDao.getCurrentOrderByDriverLogin(login);
        if (orders.isEmpty()) {
            LOG.debug("Driver with login {} has not open orders", login);
            return null;
        }
        //Searching for drives's current order
        //If size > 1 (found 'NEW' and 'IN_PROCESS'), use 'IN_PROCESS'
        int i = 0;
        if (orders.size() > 1) {
            for (Order order : orders) {
                if (order.getStatus().name().equals("IN_PROCESS")) {
                    break;
                }
                i++;
            }
        }

        return orderConverter.toOrderInfoDto(orders.get(i));
    }
}