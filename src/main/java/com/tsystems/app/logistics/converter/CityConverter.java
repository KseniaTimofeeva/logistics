package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.CityOfRoute;
import com.tsystems.app.logisticscommon.CityDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class CityConverter {

    public CityDto toCityDto(City city) {
        if (city == null) {
            return null;
        }
        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());
        return cityDto;
    }

    public List<CityDto> toCityDtoList(List<City> cities) {
        return cities
                .stream()
                .map(city ->
                        toCityDto(city))
                .collect(Collectors.toList());
    }

    public List<CityDto> routeToCityDtoList(List<CityOfRoute> route) {
        return route
                .stream()
                .map(cityOfRoute ->
                        toCityDto(cityOfRoute.getCity()))
                .collect(Collectors.toList());
    }

    public CityDto cityOfRouteToCityDto(CityOfRoute cityOfRoute) {
        CityDto dto = new CityDto();
        dto.setId(cityOfRoute.getCity().getId());
        dto.setName(cityOfRoute.getCity().getName());
        return dto;
    }
}
