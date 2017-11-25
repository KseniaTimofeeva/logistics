package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logisticscommon.CityDto;

import java.util.List;
import java.util.Map;

/**
 * Created by ksenia on 14.10.2017.
 */
public interface CityService {
    List<CityDto> getAllCities();

    List<CityDto> getRouteByOrderId(Long orderId);

    Map<Long, List<CityDto>> getCitiesToHideToUnloading(Long orderId);
}
