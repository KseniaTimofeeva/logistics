package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logisticscommon.CityDto;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
public interface CityService {
    List<CityDto> getAllCities();

//    List<CityDto> getCitiesToOrderRoute(Long orderId);
}
