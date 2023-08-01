package ua.vspelykh.atm.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.vspelykh.atm.model.dto.AccountDTO;
import ua.vspelykh.atm.model.entity.Account;

import static ua.vspelykh.atm.model.util.Columns.*;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = ID, source = ENTITY_ID)
    @Mapping(target = ACCOUNT_NUMBER, source = ACCOUNT_NUMBER)
    @Mapping(target = BALANCE, source = BALANCE)
    @Mapping(target = USER_ID, source = ID_OF_USER)
    @Mapping(target = PASSWORD, source = PASSWORD)
    @Mapping(target = ISSUE_DATE, source = ISSUE_DATE)
    AccountDTO toDTO(Account entity);

    @Mapping(target = ID, source = ID)
    @Mapping(target = ACCOUNT_NUMBER, source = ACCOUNT_NUMBER)
    @Mapping(target = BALANCE, source = BALANCE)
    @Mapping(target = ID_OF_USER, source = USER_ID)
    @Mapping(target = PASSWORD, source = PASSWORD)
    @Mapping(target = ISSUE_DATE, source = ISSUE_DATE)
    Account toEntity(AccountDTO dto);
}

