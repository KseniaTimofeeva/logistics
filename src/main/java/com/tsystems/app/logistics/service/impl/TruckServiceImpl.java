package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.converter.TruckConverter;
import com.tsystems.app.logistics.dao.impl.TruckDao;
import com.tsystems.app.logistics.dto.TruckDto;
import com.tsystems.app.logistics.entity.Truck;
import com.tsystems.app.logistics.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ksenia on 11.10.2017.
 */
@Service
@Transactional
public class TruckServiceImpl implements TruckService {

    private TruckDao truckDao;

    @Autowired
    private TruckConverter truckConverter;

    @Autowired
    public void setTruckDao(TruckDao truckDao) {
        this.truckDao = truckDao;
        truckDao.setEntityClass(Truck.class);
    }

    @Override
    public void addNewTruck(TruckDto truckDto) {
        truckDao.create(fromDtoToTruck(truckDto));
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
        return truck;
    }

    @Override
    public void updateTruck(TruckDto truckDto) {
        truckDao.update(fromDtoToTruck(truckDto));
    }
}
