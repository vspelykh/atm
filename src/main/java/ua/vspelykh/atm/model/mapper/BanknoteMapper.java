package ua.vspelykh.atm.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import static ua.vspelykh.atm.model.mapper.BaseMapperConfig.SPRING;
import static ua.vspelykh.atm.model.util.Columns.*;

@Mapper(componentModel = SPRING,uses = BaseMapperConfig.class)
public interface BanknoteMapper {

    @Mapping(source = ID, target = ID)
    @Mapping(source = ATM_ID, target = ATM_ID)
    @Mapping(source = DENOMINATION, target = DENOMINATION)
    @Mapping(source = QUANTITY, target = QUANTITY)
    BanknoteDTO toDTO(Banknote banknote);

    @Mapping(target = ID, source = ID)
    @Mapping(source = ATM_ID, target = ATM_ID)
    @Mapping(source = DENOMINATION, target = DENOMINATION)
    @Mapping(source = QUANTITY, target = QUANTITY)
    Banknote toEntity(BanknoteDTO banknoteDTO);
}

