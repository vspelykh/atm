package ua.vspelykh.atm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO implements Serializable {
    private Integer id;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private AccountDTO sourceAccount;
    private AccountDTO destinationAccount;
}
