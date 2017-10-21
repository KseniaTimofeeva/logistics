package com.tsystems.app.logistics.dto;

/**
 * Created by ksenia on 14.10.2017.
 */
public class CityDto {
    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
