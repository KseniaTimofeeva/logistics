package com.tsystems.app.logistics.converter;

import com.tsystems.app.logistics.dto.CargoDto;
import com.tsystems.app.logistics.entity.Cargo;
import org.springframework.stereotype.Component;

/**
 * Created by ksenia on 14.10.2017.
 */
@Component
public class CargoConverter {

    public CargoDto toCargoDto(Cargo cargo) {
        CargoDto cargoDto = new CargoDto();
        if (cargo == null) {
            return null;
        }
        cargoDto.setId(cargo.getId());
        cargoDto.setNumber(cargo.getNumber());
        cargoDto.setName(cargo.getName());
        cargoDto.setWeight(cargo.getWeight());
        cargoDto.setStatus(cargo.getStatus());
        return cargoDto;
    }
}
