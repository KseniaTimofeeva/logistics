package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logistics.dto.PathPointDto;
import com.tsystems.app.logistics.entity.PathPoint;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
public interface PathPointService {

    PathPointDto getPathPointById(Long id);

    void addNewPoint(PathPoint pathPoint);

    void processPathPoint(PathPointDto pointDto);

    void updatePathPoint(PathPoint pathPoint);

    void deletePathPoint(Long id);
}
