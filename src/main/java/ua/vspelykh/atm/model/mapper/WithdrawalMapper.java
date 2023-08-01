package ua.vspelykh.atm.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.vspelykh.atm.model.dto.WithdrawalDTO;
import ua.vspelykh.atm.model.entity.Withdrawal;

import static ua.vspelykh.atm.model.util.Columns.*;

@Mapper
public interface WithdrawalMapper {

    WithdrawalMapper INSTANCE = Mappers.getMapper(WithdrawalMapper.class);

    @Mapping(source = ID, target = ID)
    @Mapping(source = AMOUNT, target = AMOUNT)
    @Mapping(source = TRANSACTION_DATE, target = TRANSACTION_DATE)
    @Mapping(source = ACCOUNT, target = ACCOUNT)
    @Mapping(source = ATM, target = ATM)
    WithdrawalDTO toDTO(Withdrawal withdrawal);

    @Mapping(target = ID, source = ID)
    @Mapping(source = AMOUNT, target = AMOUNT)
    @Mapping(source = TRANSACTION_DATE, target = TRANSACTION_DATE)
    @Mapping(source = ACCOUNT, target = ACCOUNT)
    @Mapping(source = ATM, target = ATM)
    Withdrawal toEntity(WithdrawalDTO withdrawalDTO);
}

