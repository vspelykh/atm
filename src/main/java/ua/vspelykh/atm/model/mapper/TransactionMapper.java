package ua.vspelykh.atm.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.vspelykh.atm.model.dto.TransactionDTO;
import ua.vspelykh.atm.model.entity.Transaction;

import static ua.vspelykh.atm.model.util.Columns.*;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(source = ID, target = ID)
    @Mapping(source = AMOUNT, target = AMOUNT)
    @Mapping(source = TRANSACTION_DATE, target = TRANSACTION_DATE)
    @Mapping(source = SOURCE_ACCOUNT, target = SOURCE_ACCOUNT)
    @Mapping(source = DESTINATION_ACCOUNT, target = DESTINATION_ACCOUNT)
    TransactionDTO toDTO(Transaction transaction);

    @Mapping(target = ID, source = ID)
    @Mapping(source = AMOUNT, target = AMOUNT)
    @Mapping(source = TRANSACTION_DATE, target = TRANSACTION_DATE)
    @Mapping(source = SOURCE_ACCOUNT, target = SOURCE_ACCOUNT)
    @Mapping(source = DESTINATION_ACCOUNT, target = DESTINATION_ACCOUNT)
    Transaction toEntity(TransactionDTO transactionDTO);
}
