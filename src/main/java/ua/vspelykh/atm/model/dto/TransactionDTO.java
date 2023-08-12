package ua.vspelykh.atm.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class TransactionDTO extends AbstractDTO {
    private Double amount;
    private LocalDateTime transactionDate;
    private AccountDTO sourceAccount;
    private AccountDTO destinationAccount;
}
