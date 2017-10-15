package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.PathPointDto;
import com.tsystems.app.logistics.entity.PathPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class PathPointConverter {

    @Autowired
    private CargoConverter cargoConverter;

    @Autowired
    private CityConverter cityConverter;

    public PathPointDto toPathPointDto(PathPoint point) {
        PathPointDto pointDto = new PathPointDto();
        pointDto.setId(point.getId());
        if (point.getOrder() != null) {
            pointDto.setOrderId(point.getOrder().getId());
        }
        if (point.getCargo() != null) {
            pointDto.setCargo(cargoConverter.toCargoDto(point.getCargo()));
        }
        if (point.getCity() != null) {
            pointDto.setCity(cityConverter.toCityDto(point.getCity()));
        }
        pointDto.setLoading(point.getLoading());
        pointDto.setDone(point.getDone());
        return pointDto;
    }

    public List<PathPointDto> toPathPointDtoList(List<PathPoint> pointList) {
        return pointList
                .stream()
                .filter(pathPoint -> pathPoint.getVisible())
                .map(point ->
                        toPathPointDto(point))
                .collect(Collectors.toList());
    }
}
