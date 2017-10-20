package com.tsystems.app.logistics.dto;

/**
 * Created by ksenia on 19.10.2017.
 */
public class DriverProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String personalNumber;
    private CrewDriverProfileDto crew;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public CrewDriverProfileDto getCrew() {
        return crew;
    }

    public void setCrew(CrewDriverProfileDto crew) {
        this.crew = crew;
    }
}
