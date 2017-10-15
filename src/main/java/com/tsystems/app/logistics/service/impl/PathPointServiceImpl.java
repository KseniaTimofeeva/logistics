package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.PathPointConverter;
import com.tsystems.app.logistics.dao.impl.CargoDao;
import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dao.impl.PathPointDao;
import com.tsystems.app.logistics.dto.PathPointDto;
import com.tsystems.app.logistics.entity.Cargo;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.PathPoint;
import com.tsystems.app.logistics.service.api.PathPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ksenia on 14.10.2017.
 */
@Service
@Transactional
public class PathPointServiceImpl implements PathPointService {

    private PathPointDao pathPointDao;
    private CityDao cityDao;
    private CargoDao cargoDao;
    private OrderDao orderDao;

    @Autowired
    private PathPointConverter pointConverter;

    @Autowired
    public void setPathPointDao(PathPointDao pathPointDao) {
        this.pathPointDao = pathPointDao;
        pathPointDao.setEntityClass(PathPoint.class);
    }

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
        cityDao.setEntityClass(City.class);
    }

    @Autowired
    public void setCargoDao(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
        cargoDao.setEntityClass(Cargo.class);
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        orderDao.setEntityClass(Order.class);
    }

    @Override
    public PathPointDto getPathPointById(Long id) {
        PathPoint point = pathPointDao.findOneById(id);
        return pointConverter.toPathPointDto(point);
    }

    @Override
    public void processPathPoint(PathPointDto pointDto) {
        PathPoint point = new PathPoint();
        Cargo cargo = new Cargo();

        if (pointDto.getId() != null) {
            point.setId(pointDto.getId());
            cargo = cargoDao.findOneById(pointDto.getCargo().getId());
        }

        cargo.setName(pointDto.getCargo().getName());
        cargo.setNumber(pointDto.getCargo().getNumber());
        cargo.setWeight(pointDto.getCargo().getWeight());
        cargo.setStatus(pointDto.getCargo().getStatus());

        if (pointDto.getId() != null) {
            cargo = cargoDao.update(cargo);
        } else {
            cargo = cargoDao.create(cargo);
        }

        City city = cityDao.findOneById(pointDto.getCity().getId());

        Order order = orderDao.findOneById(pointDto.getOrderId());

        point.setCargo(cargo);
        point.setCity(city);
        point.setLoading(pointDto.getLoading());
        point.setDone(pointDto.getDone());
        point.setOrder(order);

        if (pointDto.getId() != null) {
            updatePathPoint(point);
        } else {
            addNewPoint(point);
        }
    }

    @Override
    public void updatePathPoint(PathPoint pathPoint) {
        pathPointDao.update(pathPoint);
    }

    @Override
    public void deletePathPoint(Long id) {
        cargoDao.delete(pathPointDao.findOneById(id).getCargo());
        pathPointDao.deleteById(id);
    }

    @Override
    public void addNewPoint(PathPoint pathPoint) {
        pathPointDao.create(pathPoint);
    }
}
