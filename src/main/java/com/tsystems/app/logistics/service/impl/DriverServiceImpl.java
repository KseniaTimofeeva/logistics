package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.DriverConverter;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.DriverDto;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.SecurityRole;
import com.tsystems.app.logistics.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 08.10.2017.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    private UserDao userDao;

    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        userDao.setEntityClass(User.class);
    }

    @Override
    public void processDriver(DriverDto driverDto) {
        if (null != driverDto.getId()) {
            updateDriver(driverDto);
        } else {
            addNewDriver(driverDto);
        }
    }

    @Override
    public void addNewDriver(DriverDto driverDto) {
        userDao.create(fromDtoToUser(driverDto));
    }

    @Override
    public void updateDriver(DriverDto driverDto) {
        userDao.update(fromDtoToUser(driverDto));
    }

    private User fromDtoToUser(DriverDto driverDto) {
        User user = new User();
        if (driverDto.getId() != null) {
            user.setId(driverDto.getId());
        }
        user.setFirstName(driverDto.getFirstName());
        user.setLastName(driverDto.getLastName());
        user.setPersonalNumber(driverDto.getPersonalNumber());
        user.setLogin(driverDto.getLogin());
        user.setPassword(driverDto.getPassword());
        user.setRole(SecurityRole.ROLE_DRIVER);
        return user;
    }

    @Override
    public List<DriverDto> getAllDrivers() {
        List<User> allUsers = userDao.getAllUsers();
        return driverConverter.toDriverDtoList(allUsers);
    }

    @Override
    public void deleteDriver(Long id) {
        userDao.deleteById(id);
    }

    @Override
    public DriverDto getDriverById(Long id) {
        User user = userDao.findOneById(id);
        return driverConverter.toDriverDto(user);
    }
}
