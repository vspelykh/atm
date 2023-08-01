package ua.vspelykh.atm.model.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.vspelykh.atm.model.dto.TransactionDTO;
import ua.vspelykh.atm.model.entity.Transaction;

@Component
@RequiredArgsConstructor
public class TransactionConverter {

    private final AccountConverter accountConverter;

    public TransactionDTO toDto(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .sourceAccount(accountConverter.toDto(transaction.getSourceAccount()))
                .destinationAccount(accountConverter.toDto(transaction.getDestinationAccount()))
                .build();
    }

    public Transaction toEntity(TransactionDTO transactionDTO) {
        return Transaction.builder()
                .amount(transactionDTO.getAmount())
                .transactionDate(transactionDTO.getTransactionDate())
                .sourceAccount(accountConverter.toEntity(transactionDTO.getSourceAccount()))
                .destinationAccount(accountConverter.toEntity(transactionDTO.getDestinationAccount()))
                .build();
    }
}
