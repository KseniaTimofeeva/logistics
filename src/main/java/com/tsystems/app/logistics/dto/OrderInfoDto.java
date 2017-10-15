package com.tsystems.app.logistics.dto;

import com.tsystems.app.logistics.entity.Crew;
import com.tsystems.app.logistics.entity.PathPoint;

import java.util.List;

/**
 * Created by ksenia on 13.10.2017.
 */
public class OrderInfoDto {

    private Long id;
    private String number;
    private Boolean finished = false;
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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
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
