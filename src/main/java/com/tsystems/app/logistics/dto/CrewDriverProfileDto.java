package com.tsystems.app.logistics.dto;

import java.util.List;

/**
 * Created by ksenia on 19.10.2017.
 */
public class CrewDriverProfileDto {
    private Long id;
    private TruckDto truck;
    private List<DriverShortDto> users;
    private OrderDto order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TruckDto getTruck() {
        return truck;
    }

    public void setTruck(TruckDto truck) {
        this.truck = truck;
    }

    public List<DriverShortDto> getUsers() {
        return users;
    }

    public void setUsers(List<DriverShortDto> users) {
        this.users = users;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }
}
