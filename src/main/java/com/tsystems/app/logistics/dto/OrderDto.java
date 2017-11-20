package com.tsystems.app.logistics.dto;

import com.tsystems.app.logisticscommon.enums.OrderStatus;

/**
 * Created by ksenia on 13.10.2017.
 */
public class OrderDto {
    private Long id;
    private String number;
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
