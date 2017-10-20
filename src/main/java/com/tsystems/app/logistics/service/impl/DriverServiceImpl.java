package com.tsystems.app.logistics.service.impl;

import com.google.maps.model.LatLng;
import com.tsystems.app.logistics.converter.DriverConverter;
import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.DriverDto;
import com.tsystems.app.logistics.dto.DriverProfileDto;
import com.tsystems.app.logistics.dto.SuitableDriverDto;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.SecurityRole;
import com.tsystems.app.logistics.service.api.DriverService;
import com.tsystems.app.logistics.utils.GeoUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ksenia on 08.10.2017.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService {
    private static final Logger LOG = LogManager.getLogger(DriverServiceImpl.class);

    private UserDao userDao;
    private CityDao cityDao;

    @Autowired
    private GeoUtils geoUtils;
    @Autowired
    private DriverConverter driverConverter;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        userDao.setEntityClass(User.class);
    }

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
        cityDao.setEntityClass(City.class);
    }


    @Override
    public void processDriver(DriverDto driverDto) {
        if (driverDto.getId() != null) {
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

    @Override
    public SuitableDriverDto getSuitableDriversForOrder(Long orderId) {

        Long d = geoUtils.getDistanceBetweenWithGoogleApi(new LatLng(59.95, 30.31667), new LatLng(55.75583, 37.61778));

        LOG.debug("*************************************************** {}", d);

        List<User> drivers = userDao.getSuitableDrivers(orderId);
        SuitableDriverDto suitableDriverDto = new SuitableDriverDto();
        suitableDriverDto.setDrivers(driverConverter.toDriverDtoList(drivers));
        suitableDriverDto.setNotSuitableDrivers(new ArrayList<>());
        return suitableDriverDto;
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
        user.setOnOrder(driverDto.getOnOrder());
        City city = cityDao.findOneById(driverDto.getCurrentCity().getId());
        user.setCurrentCity(city);
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

    @Override
    public DriverProfileDto getDriverProfileByLogin(String login) {
        List<User> drivers = userDao.getProfile(login);
        if (drivers.isEmpty()) {
            drivers = userDao.getUserByLogin(login);
        }
        return driverConverter.toDriverProfileDto(drivers.get(0));
    }
}
