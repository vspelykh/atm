package ua.vspelykh.atm.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.vspelykh.atm.model.dto.ATMDTO;
import ua.vspelykh.atm.model.entity.ATM;

import static ua.vspelykh.atm.model.mapper.BaseMapperConfig.SPRING;
import static ua.vspelykh.atm.model.util.Columns.*;

@Mapper(componentModel = SPRING,uses = BaseMapperConfig.class)
public interface ATMMapper {

    @Mapping(source = ID, target = ID)
    @Mapping(source = LOCATION, target = LOCATION)
    @Mapping(source = AVAILABILITY, target = AVAILABILITY)
    ATMDTO toDTO(ATM atm);

    @Mapping(target = ID, source = ID)
    @Mapping(source = LOCATION, target = LOCATION)
    @Mapping(source = AVAILABILITY, target = AVAILABILITY)
    ATM toEntity(ATMDTO atmDTO);
}

