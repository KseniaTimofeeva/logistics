package com.tsystems.app.logistics.service.api;

import com.tsystems.app.logisticscommon.GeneralInfoDto;

/**
 * Created by ksenia on 11.11.2017.
 */
public interface GeneralInfoService {

    GeneralInfoDto getGeneralInfo();

    GeneralInfoDto setDriverInfo(GeneralInfoDto dto);

    GeneralInfoDto setTruckInfo(GeneralInfoDto dto);
}
