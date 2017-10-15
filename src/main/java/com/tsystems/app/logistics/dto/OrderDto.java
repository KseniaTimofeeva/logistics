package com.tsystems.app.logistics.dto;

/**
 * Created by ksenia on 13.10.2017.
 */
public class OrderDto {
    private Long id;
    private String number;
    private Boolean finished = false;

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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
