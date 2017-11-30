package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.DBUtils;
import com.tsystems.app.logistics.TestConfig;
import com.tsystems.app.logistics.dto.OrderDto;
import com.tsystems.app.logisticscommon.enums.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
public class OrderServiceImplTest {
    private static boolean setup;
    private static final String ORDER_NUMBER = "1-1";
    private static final String NEW_ORDER_NUMBER = "2-2";
    @Autowired
    private DBUtils dbUtils;

    @Autowired
    private OrderServiceImpl orderService;

    @Before
    public void init() {
        if(setup) return;

        OrderDto newOrderDto = new OrderDto();
        newOrderDto.setNumber(ORDER_NUMBER);
        newOrderDto.setId(1L);
        orderService.addNewOrder(newOrderDto);
        setup = true;
    }

    @Test
    public void getAllOrders() throws Exception {
        assertNotEquals(0,orderService.getAllOrders());
    }

    @Test
    public void addNewOrder() throws Exception {
        OrderDto newOrderDto = new OrderDto();
        newOrderDto.setNumber(NEW_ORDER_NUMBER);
        newOrderDto.setId(2L);
        orderService.addNewOrder(newOrderDto);

        assertEquals(orderService.getOrderById(2L).getNumber(), NEW_ORDER_NUMBER);
    }

    @Test
    public void closeOrder() throws Exception {
        orderService.closeOrder(1L);
        assertEquals(orderService.getOrderById(1L).getStatus(), OrderStatus.FINISHED);
    }

    @Test
    public void getOrderById() throws Exception {
        assertEquals(orderService.getOrderById(1L).getNumber(), ORDER_NUMBER);
    }

}