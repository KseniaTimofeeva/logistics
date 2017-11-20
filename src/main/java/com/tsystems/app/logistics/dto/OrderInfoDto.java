package com.tsystems.app.logistics.dto;

import com.tsystems.app.logisticscommon.enums.OrderStatus;

import java.util.List;

/**
 * Created by ksenia on 13.10.2017.
 */
public class OrderInfoDto {

    private Long id;
    private String number;
    private OrderStatus status;
    private List<PathPointDto> pathPoints;
    private CrewDto crew;

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

    public List<PathPointDto> getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(List<PathPointDto> pathPoints) {
        this.pathPoints = pathPoints;
    }

    public CrewDto getCrew() {
        return crew;
    }

    public void setCrew(CrewDto crew) {
        this.crew = crew;
    }
}
