package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.CityConverter;
import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dao.impl.PathPointDao;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.PathPoint;
import com.tsystems.app.logistics.service.api.CityService;
import com.tsystems.app.logisticscommon.CityDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ksenia on 14.10.2017.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {
    private static final Logger LOG = LogManager.getLogger(CityServiceImpl.class);

    private CityDao cityDao;
    private OrderDao orderDao;
    private PathPointDao pathPointDao;

    @Autowired
    private CityConverter cityConverter;

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
        cityDao.setEntityClass(City.class);
    }
    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        orderDao.setEntityClass(Order.class);
    }
    @Autowired
    public void setPathPointDao(PathPointDao pathPointDao) {
        this.pathPointDao = pathPointDao;
        pathPointDao.setEntityClass(PathPoint.class);
    }

    @Override
    public List<CityDto> getAllCities() {
        List<City> cities = cityDao.getAllCities();
        return cityConverter.toCityDtoList(cities);
    }

    @Override
    public List<CityDto> getRouteByOrderId(Long orderId) {
        Order order = orderDao.findOneById(orderId);
        return cityConverter.routeToCityDtoList(order.getRoute());
    }

    @Override
    public Map<Long, List<CityDto>> getCitiesToHideToUnloading(Long orderId) {
        Map<Long, List<CityDto>> result = new HashMap<>();
        Order order = orderDao.findOneById(orderId);
        if (order.getRoute() == null) {
            return null;
        }
        List<CityDto> route = cityConverter.routeToCityDtoList(order.getRoute());
        List<PathPoint> pointsToUnload = pathPointDao.getPathPointsWithCargoToUnload(orderId);
        for (PathPoint point : pointsToUnload) {
            List<CityDto> cityDtoList = new ArrayList<>();
            for (CityDto cityDto : route) {
                if (!cityDto.getId().equals(point.getCity().getId())) {
                    cityDtoList.add(cityDto);
                } else {
                    cityDtoList.add(cityDto);
                    break;
                }
            }
            result.put(point.getCargo().getId(), cityDtoList);
        }
        return result;
    }

    @Override
    public List<CityDto> getCitiesToUnload(Long orderId, Long pathPointId) {
        List<CityDto> result = new ArrayList<>();
        List<CityDto> route = getRouteByOrderId(orderId);
        PathPoint point = pathPointDao.findOneById(pathPointId);
        List<PathPoint> pathPointWithSameCargoLoad = pathPointDao.getPathPointWithSameCargoLoad(point.getCargo().getId());
        boolean cityToAdd = false;
        for (CityDto cityDto : route) {
            if (cityDto.getId().equals(pathPointWithSameCargoLoad.get(0).getCity().getId())) {
                cityToAdd = true;
                continue;
            }
            if (cityToAdd) {
                result.add(cityDto);
            }
        }
        return result;
    }

}
