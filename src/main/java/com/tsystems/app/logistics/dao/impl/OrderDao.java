package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.dao.impl.GenericDaoImpl;
import com.tsystems.app.logistics.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

}
