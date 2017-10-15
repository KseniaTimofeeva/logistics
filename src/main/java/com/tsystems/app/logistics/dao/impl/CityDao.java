package com.tsystems.app.logistics.dao.impl;

import com.tsystems.app.logistics.entity.City;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
@Repository
public class CityDao extends GenericDaoImpl<City>{

    public List<City> getAllCities() {
        return em.createNamedQuery(City.GET_ALL_CITIES, City.class).getResultList();
    }
}
