package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.TruckConverter;
import com.tsystems.app.logistics.dao.impl.CityDao;
import com.tsystems.app.logistics.dao.impl.OrderDao;
import com.tsystems.app.logistics.dao.impl.TruckDao;
import com.tsystems.app.logistics.dto.SuitableTruckDto;
import com.tsystems.app.logistics.dto.TruckDto;
import com.tsystems.app.logistics.entity.City;
import com.tsystems.app.logistics.entity.Order;
import com.tsystems.app.logistics.entity.PathPoint;
import com.tsystems.app.logistics.entity.Truck;
import com.tsystems.app.logistics.service.api.TruckService;
import com.tsystems.app.logisticscommon.TruckFullDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ksenia on 11.10.2017.
 */
@Service
@Transactional
public class TruckServiceImpl implements TruckService {
    private static final Logger LOG = LogManager.getLogger(TruckServiceImpl.class);

    private TruckDao truckDao;
    private OrderDao orderDao;
    private CityDao cityDao;

    @Autowired
    private TruckConverter truckConverter;

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
        truckDao.setEntityClass(Truck.class);
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        orderDao.setEntityClass(Order.class);
    }

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
        cityDao.setEntityClass(City.class);
    }

    @Override
    public void addNewTruck(TruckDto truckDto) {
        validateNewTruckForm(truckDto);
        truckDto.setOnOrder(false);
        truckDao.create(fromDtoToTruck(truckDto));
    }

    /**
     * Validate new truck form
     *
     * @param truckDto dto with new truck information
     * @return true if number plate is unique,
     * else throw exception
     */
    private boolean validateNewTruckForm(TruckDto truckDto) {
        if (truckDto.getNumberPlate() == null || truckDto.getNumberPlate().equals("")) {
            throw new RuntimeException("Value for filed 'Number plate' is required");
        }
        List<Truck> trucks = truckDao.newTruckValidate(truckDto.getNumberPlate());
        if (!trucks.isEmpty()) {
            throw new RuntimeException("Truck with specified number plate is already registered");
        }
        return true;
    }

    @Override
    public List<TruckDto> getAllTrucks() {
        List<Truck> allTrucks = truckDao.getAllTrucks();
        return truckConverter.toTruckDtoList(allTrucks);
    }

    @Override
    public void deleteTruck(Long id) {
        truckDao.deleteById(id);
    }

    @Override
    public TruckDto getTruckById(Long id) {
        Truck truck = truckDao.findOneById(id);
        return truckConverter.toTruckDto(truck);
    }

    @Override
    public void processTruck(TruckDto truckDto) {
        if (truckDto.getId() != null) {
            updateTruck(truckDto);
        } else {
            addNewTruck(truckDto);
        }
    }

    private Truck fromDtoToTruck(TruckDto truckDto) {
        Truck truck = new Truck();
        if (truckDto.getId() != null) {
            truck.setId(truckDto.getId());
        }
        truck.setNumberPlate(truckDto.getNumberPlate());
        truck.setWorkingShift(truckDto.getWorkingShift());
        truck.setCapacity(truckDto.getCapacity());
        truck.setFunctioning(truckDto.getFunctioning());
        truck.setOnOrder(truckDto.getOnOrder());
        City city = cityDao.findOneById(truckDto.getCurrentCity().getId());
        truck.setCurrentCity(city);
        return truck;
    }

    @Override
    public void updateTruck(TruckDto truckDto) {
        truckDao.update(fromDtoToTruck(truckDto));
    }

    @Override
    public SuitableTruckDto getSuitableTruckByOrderId(Long id) {

        float totalWeight = 0;
        float maxTotalWeight = 0;
        boolean isCurrentTruckSuitable = false;

        Order order = orderDao.findOneById(id);
        LOG.debug("Searching for suitable trucks for order {}", order.getNumber());
        for (PathPoint pp : order.getPathPoints()) {
            if (!pp.getVisible()) {
                continue;
            }
            if (pp.getLoading()) {
                totalWeight += pp.getCargo().getWeight();
            } else {
                totalWeight -= pp.getCargo().getWeight();
            }
            if (totalWeight > maxTotalWeight) {
                maxTotalWeight = totalWeight;
            }
        }
        List<Truck> suitableTrucks = truckDao.getSuitableTrucks(maxTotalWeight / 1000.0f);
        if (order.getCrew() != null) {
            if (order.getCrew().getTruck() != null) {
                Float currentTruckCapacity = order.getCrew().getTruck().getCapacity();
                if (currentTruckCapacity >= (maxTotalWeight / 1000.0f)) {
                    isCurrentTruckSuitable = true;
                }
            }
        }

        SuitableTruckDto suitableTruckDto = new SuitableTruckDto();
        suitableTruckDto.setIsCurrentTruckSuitable(isCurrentTruckSuitable);
        suitableTruckDto.setTrucks(truckConverter.toTruckDtoList(suitableTrucks));

        return suitableTruckDto;
    }

    @Override
    public List<TruckFullDto> getTrucksFullInfo() {
        TruckFullDto truckFullDto = new TruckFullDto();
        return truckConverter.toTruckFullDtoList(truckDao.getTrucksFullInfo());
    }
}
