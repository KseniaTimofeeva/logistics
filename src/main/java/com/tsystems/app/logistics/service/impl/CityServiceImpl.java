package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.CityConverter;
import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dto.CityDto;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.service.api.CityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {
    private static final Logger LOG = LogManager.getLogger(CityServiceImpl.class);

    private CityDao cityDao;

    @Autowired
    private CityConverter cityConverter;

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
        cityDao.setEntityClass(City.class);
    }

    @Override
    public List<CityDto> getAllCities() {
        List<City> cities = cityDao.getAllCities();
        return cityConverter.toCityDtoList(cities);
    }
}
