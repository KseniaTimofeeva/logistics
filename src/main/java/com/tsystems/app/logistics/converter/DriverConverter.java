package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.DriverDto;
import com.tsystems.app.logistics.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class DriverConverter {

    public DriverDto toDriverDto(User user) {
        DriverDto driverDto = new DriverDto();
        driverDto.setId(user.getId());
        driverDto.setFirstName(user.getFirstName());
        driverDto.setLastName(user.getLastName());
        driverDto.setPersonalNumber(user.getPersonalNumber());
        driverDto.setLogin(user.getLogin());
        driverDto.setPassword(user.getPassword());
        return driverDto;
    }

    public List<DriverDto> toDriverDtoList(List<User> userList) {
        return userList
                .stream()
                .map(user ->
                        toDriverDto(user))
                .collect(Collectors.toList());
    }
}
