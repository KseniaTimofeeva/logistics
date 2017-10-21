package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.PathPointConverter;
import com.tsystems.app.logistics.dao.impl.CargoDao;
import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dao.impl.PathPointDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.PathPointDto;
import com.tsystems.app.logistics.entity.Cargo;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.PathPoint;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.OrderStatus;
import com.tsystems.app.logistics.service.api.PathPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private UserDao userDao;

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

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        userDao.setEntityClass(User.class);
    }


    @Override
    public PathPointDto getPathPointById(Long id) {
        PathPoint point = pathPointDao.findOneById(id);
        return pointConverter.toPathPointDto(point);
    }

    @Override
    public void processPathPoint(PathPointDto pointDto) {
        PathPoint point = new PathPoint();

        boolean isNewPoint = false;
        boolean isNewCargo = false;
        if (pointDto.getId() == null) {
            isNewPoint = true;
        }
        if (pointDto.getCargo().getId() == null) {
            isNewCargo = true;
        }

        Cargo cargo;

        if (isNewCargo) {
            cargo = new Cargo();
        } else {
            cargo = cargoDao.findOneById(pointDto.getCargo().getId());

            if (pointDto.getId() != null) {
                point.setId(pointDto.getId());
            }
        }

        if (isNewCargo || !isNewPoint) {
            cargo.setName(pointDto.getCargo().getName());
            cargo.setNumber(pointDto.getCargo().getNumber());
            cargo.setWeight(pointDto.getCargo().getWeight());
            cargo.setStatus(pointDto.getCargo().getStatus());
        }

        if (!isNewPoint) {
            cargo = cargoDao.update(cargo);
        } else if (isNewCargo) {
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

    @Override
    public List<PathPointDto> getPathPointsWithCargoToUnload(Long orderId) {
        List<PathPoint> points = pathPointDao.getPathPointsWithCargoToUnload(orderId);
        return pointConverter.toPathPointDtoList(points);
    }

    @Override
    public boolean hasCargoToUnload(Long orderId) {
        List<PathPointDto> points = getPathPointsWithCargoToUnload(orderId);
        return !points.isEmpty();
    }

    @Override
    public void closePathPoint(Long pointId) {
        PathPoint closedPoint = pathPointDao.findOneById(pointId);
        closedPoint.setDone(true);
        pathPointDao.update(closedPoint);
        Order order = orderDao.findOneById(closedPoint.getOrder().getId());
        boolean isFinishedOrder = true;
        for (PathPoint point : order.getPathPoints()) {
            if (!point.getDone()) {
                isFinishedOrder = false;
                break;
            }
        }
        if (isFinishedOrder) {
            for (User driver : order.getCrew().getUsers()) {
                driver.setOnOrder(false);
                userDao.update(driver);
            }
        }
    }

}
