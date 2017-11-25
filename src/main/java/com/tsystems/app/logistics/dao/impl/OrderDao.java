package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.entity.CityOfRoute;
import com.tsystems.app.logistics.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ksenia on 13.10.2017.
 */
@Repository
public class OrderDao extends GenericDaoImpl<Order> {

    public List<Order> getAllOrders() {
        return em.createNamedQuery(Order.GET_ALL_ORDERS, Order.class)
                .getResultList();
    }

    public List<Order> getCurrentOrderByDriverLogin(String login) {
        return em.createNamedQuery(Order.GET_CURRENT_ORDER_BY_DRIVER_LOGIN, Order.class)
                .setParameter("login", login)
                .getResultList();
    }

    public List<Order> newOrderValidate(String number) {
        return em.createNamedQuery(Order.VALIDATE_NEW_ORDER, Order.class)
                .setParameter("number", number)
                .getResultList();
    }

    public List<Order> getLastOrders(int maxResult) {
        return em.createNamedQuery(Order.GET_ALL_ORDERS, Order.class)
                .setMaxResults(maxResult)
                .getResultList();
    }

    public List<CityOfRoute> getRouteByOrderId(Long orderId) {
        Order order = findOneById(orderId);
        return order.getRoute();
    }

}
