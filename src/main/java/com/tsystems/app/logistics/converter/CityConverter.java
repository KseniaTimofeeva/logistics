package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.CityDto;
import com.tsystems.app.logistics.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class CityConverter {

    public CityDto toCityDto(City city) {
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
}
