package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.PathPointConverter;
import com.tsystems.app.logistics.dao.impl.CargoDao;
import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dao.impl.PathPointDao;
import com.tsystems.app.logistics.dao.impl.TruckDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.CargoDto;
import com.tsystems.app.logistics.dto.PathPointDto;
import com.tsystems.app.logistics.entity.Cargo;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.PathPoint;
import com.tsystems.app.logistics.entity.Truck;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.entity.enums.CargoStatus;
import com.tsystems.app.logistics.service.api.DriverService;
import com.tsystems.app.logistics.service.api.GeneralInfoService;
import com.tsystems.app.logistics.service.api.OrderService;
import com.tsystems.app.logistics.service.api.PathPointService;
import com.tsystems.app.logistics.service.api.TruckService;
import com.tsystems.app.logisticscommon.enums.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by ksenia on 14.10.2017.
 */
@Service
@Transactional
public class PathPointServiceImpl implements PathPointService {
    private static final Logger LOG = LogManager.getLogger(PathPointServiceImpl.class);

    private PathPointDao pathPointDao;
    private CityDao cityDao;
    private CargoDao cargoDao;
    private OrderDao orderDao;
    private UserDao userDao;
    private TruckDao truckDao;

    @Autowired
    private PathPointConverter pointConverter;
    @Autowired
    private DriverService driverService;
    @Autowired
    private TruckService truckService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GeneralInfoService generalInfoService;

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

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
        truckDao.setEntityClass(Truck.class);
    }


    @Override
    public PathPointDto getPathPointById(Long id) {
        PathPoint point = pathPointDao.findOneById(id);
        return pointConverter.toPathPointDto(point);
    }


    /**
     * Convert way point dto to way point entity and call method for creating or updating entity
     *
     * @param pointDto way point dto that contains fields should be set to point entity
     */
    @Override
    public void processPathPoint(PathPointDto pointDto) {
        LOG.debug("Processing path point {} (cargo {}) of order {}", pointDto.getId(), pointDto.getCargo().getNumber(), pointDto.getOrderId());

        Order order = orderDao.findOneById(pointDto.getOrderId());
        if (order.getStatus().equals(OrderStatus.FINISHED)) {
            return;
        }
        if (pointDto.getDone() == null) {
            pointDto.setDone(false);
        }
        if (pointDto.getDone()) {
            return;
        }
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
            validateNewCargo(pointDto.getCargo());
        } else {
            if (pointDto.getLoading()) {
                validateNewCargo(pointDto.getCargo());
            }
            cargo = cargoDao.findOneById(pointDto.getCargo().getId());
            if (pointDto.getId() != null) {
                point.setId(pointDto.getId());
            }
        }

        if (isNewCargo || !isNewPoint) {
            cargo.setName(pointDto.getCargo().getName());
            cargo.setNumber(pointDto.getCargo().getNumber());
            cargo.setWeight(pointDto.getCargo().getWeight());
            if (pointDto.getCargo().getStatus() == null) {
                cargo.setStatus(CargoStatus.NEW);
            } else {
                cargo.setStatus(pointDto.getCargo().getStatus());
            }
        }

        if (!isNewPoint) {
            cargo = cargoDao.update(cargo);
        } else if (isNewCargo) {
            LOG.trace("Add new cargo {}", pointDto.getCargo().getNumber());
            cargo = cargoDao.create(cargo);
        }

        City city = cityDao.findOneById(pointDto.getCity().getId());
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

    /**
     * Validate new cargo form
     *
     * @param cargoDto dto with new cargo information
     * @return true if number is unique,
     * else throw exception
     */
    private boolean validateNewCargo(CargoDto cargoDto) {
        if (StringUtils.isEmpty(cargoDto.getNumber())) {
            throw new RuntimeException("error.form.empty");
        }
        if (StringUtils.isEmpty(cargoDto.getName())) {
            throw new RuntimeException("error.form.empty");
        }
        if (cargoDto.getWeight() == null || cargoDto.getWeight() == 0) {
            throw new RuntimeException("error.form.empty");
        }
        return true;
    }

    @Override
    public void updatePathPoint(PathPoint pathPoint) {
        if (pathPoint.getDone()) {
            return;
        }
        LOG.trace("Update path point {} (cargo {}) of order {}", pathPoint.getId(), pathPoint.getCargo().getNumber(), pathPoint.getOrder().getNumber());
        pathPointDao.update(pathPoint);
    }

    @Override
    public void deletePathPoint(Long id) {
        PathPoint point = pathPointDao.findOneById(id);
        if (point.getDone()) {
            return;
        }
        if (point.getOrder().getStatus().equals(OrderStatus.FINISHED)) {
            return;
        }
        if (point.getLoading()) {
            List<PathPoint> points = pathPointDao.getPathPointWithSameCargoUnload(point.getCargo().getId());
            if (!points.isEmpty()) {
                throw new RuntimeException("error.logical.relatedPoints");
            }
        }

        pathPointDao.deleteById(id);
    }

    @Override
    public void addNewPoint(PathPoint pathPoint) {
        LOG.trace("Add new path point to order {}", pathPoint.getOrder().getNumber());
        pathPointDao.create(pathPoint);
    }

    /**
     * Searching for way points containing cargo that has been loaded but has not been unloaded yet
     *
     * @param orderId id of the order for which way points with unloaded cargo is searching for
     * @return list of way points dto
     */
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


    /**
     * Set that cargo is loaded or unloaded in the destination city
     * If this way point is the last point of the order set driver status 'vacant'
     *
     * @param pointId id of the way point which contains loaded/unloaded cargo
     */
    @Override
    public void closePathPoint(Long pointId) {
        PathPoint closedPoint = pathPointDao.findOneById(pointId);
        Order order = orderDao.findOneById(closedPoint.getOrder().getId());

        if (!order.getCrew().getTruck().getFunctioning()) {
            return;
        }

        if (closedPoint.getLoading() != null) {
            Cargo closedPointCargo = closedPoint.getCargo();
            if (closedPoint.getLoading()) {
                closedPointCargo.setStatus(CargoStatus.SHIPPING);
            } else {
                closedPointCargo.setStatus(CargoStatus.DELIVERED);
            }
            closedPointCargo = cargoDao.update(closedPointCargo);

            closedPoint.setCargo(closedPointCargo);
        }
        closedPoint.setDone(true);
        closedPoint = pathPointDao.update(closedPoint);
        boolean isFinishedOrder = orderService.isAllPointsDoneByOrder(order);

        City city = closedPoint.getCity();
        for (User driver : order.getCrew().getUsers()) {
            driver.setCurrentCity(city);
            if (isFinishedOrder) {
                driver.setOnOrder(false);
            }
            driver = userDao.update(driver);
            driverService.updateBoardUpdateDriver(driver);
        }
        Truck truck = order.getCrew().getTruck();
        truck.setCurrentCity(city);
        if (isFinishedOrder) {
            LOG.debug("All way points of order {} are finished", order.getNumber());
            truck.setOnOrder(false);
        }
        truck = truckDao.update(truck);
        truckService.updateBoardUpdateTruck(truck);
        generalInfoService.updateBoardGeneralInfo(null);
    }
}
