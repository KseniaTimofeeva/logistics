package com.tsystems.app.logistics;

import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DBUtils {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CityDao cityDao;

    @Transactional
    public void createStubDataBase() {
        userDao.setEntityClass(User.class);
        cityDao.setEntityClass(City.class);

        createManager();
        createCity();
        createDriver();
    }

    @Transactional
    void createCity() {
        City city = new City();
        city.setId(1L);
        city.setName("SPb");
        cityDao.update(city);
    }

    @Transactional
    void createManager() {
        User manager = new User();
        manager.setLastName("Manager");
        manager.setFirstName("ML");
        manager.setLogin("manager");
        manager.setPassword("manager");
        manager.setRole(SecurityRole.ROLE_MANAGER);
        manager.setVisible(true);
        manager.setId(1L);

        userDao.update(manager);
    }

    @Transactional
    void createDriver() {
        User driver = new User();
        driver.setLastName("Driver");
        driver.setFirstName("ML");
        driver.setLogin("driver");
        driver.setPassword("driver");
        driver.setRole(SecurityRole.ROLE_DRIVER);
        driver.setVisible(true);
        driver.setPersonalNumber("p-num-1");
        driver.setCurrentCity(cityDao.findOneById(1L));
        driver.setId(2L);

        userDao.update(driver);
    }
}