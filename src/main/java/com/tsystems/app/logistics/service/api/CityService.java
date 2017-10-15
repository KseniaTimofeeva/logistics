package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.CityDto;
import com.tsystems.app.logistics.entity.City;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
public interface CityService {
    List<CityDto> getAllCities();
}
