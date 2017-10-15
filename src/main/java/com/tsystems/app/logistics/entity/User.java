package com.tsystems.app.logistics.entity;

import com.tsystems.app.logistics.entity.enums.DriverStatus;
import com.tsystems.app.logistics.entity.enums.SecurityRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by ksenia on 03.10.2017.
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = User.GET_BY_LOGIN,
                query = "select u from User u where u.login = :login"),
        @NamedQuery(name = User.GET_ALL_DRIVERS,
                query = "select u from User u where u.role = 'ROLE_DRIVER' and u.visible = true")
})
public class User extends BaseEntity {

    public static final String GET_BY_LOGIN = "User.userByLogin";
    public static final String GET_ALL_DRIVERS = "User.getAllDrivers";

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "personal_number", unique = true)
    private String personalNumber;

    @Column(unique = true)
    private String login;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private SecurityRole role;

    @ManyToMany(mappedBy = "users")
    private List<Crew> crews;

    @OneToMany(mappedBy = "user")
    private List<TimeTrack> timeTracks;

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

    public List<Crew> getCrews() {
        return crews;
    }

    public void setCrews(List<Crew> crews) {
        this.crews = crews;
    }

    public List<TimeTrack> getTimeTracks() {
        return timeTracks;
    }

    public void setTimeTracks(List<TimeTrack> timeTracks) {
        this.timeTracks = timeTracks;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SecurityRole getRole() {
        return role;
    }

    public void setRole(SecurityRole role) {
        this.role = role;
    }
}
