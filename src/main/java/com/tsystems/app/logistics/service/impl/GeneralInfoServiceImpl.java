package com.tsystems.app.logistics.service.impl;

import com.tsystems.app.logistics.dao.impl.TruckDao;
import com.tsystems.app.logistics.dao.impl.UserDao;
import com.tsystems.app.logistics.dto.ChangeEvent;
import com.tsystems.app.logistics.entity.Truck;
import com.tsystems.app.logistics.entity.User;
import com.tsystems.app.logistics.service.api.GeneralInfoService;
import com.tsystems.app.logisticscommon.GeneralInfoDto;
import com.tsystems.app.logisticscommon.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ksenia on 11.11.2017.
 */
@Service
@Transactional
public class GeneralInfoServiceImpl implements GeneralInfoService {

    private UserDao userDao;
    private TruckDao truckDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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
    public GeneralInfoDto getGeneralInfo() {
        GeneralInfoDto generalInfoDto = new GeneralInfoDto();
        generalInfoDto = setDriverInfo(generalInfoDto);
        generalInfoDto = setTruckInfo(generalInfoDto);
        return generalInfoDto;
    }

    @Override
    public GeneralInfoDto setDriverInfo(GeneralInfoDto dto) {
        dto.setDriverQty(userDao.getDriverQty());
        dto.setVacantDriverQty(userDao.getVacantDriverQty());
        dto.setNotAvailableDriverQty(userDao.getNotAvailableDriverQty());
        return dto;
    }

    @Override
    public GeneralInfoDto setTruckInfo(GeneralInfoDto dto) {
        dto.setTruckQty(truckDao.getTruckQty());
        dto.setVacantTruckQty(truckDao.getVacantTruckQty());
        dto.setOnOrderTruckQty(truckDao.getOnOrderTruckQty());
        dto.setNotWorkingTruckQty(truckDao.getNotWorkingTruckQty());
        return dto;
    }

    @Override
    public void updateBoardGeneralInfo(Boolean isDriverGeneralInfo) {
        applicationEventPublisher.publishEvent(new ChangeEvent(MessageType.GENERAL, isDriverGeneralInfo,null));
    }
}
