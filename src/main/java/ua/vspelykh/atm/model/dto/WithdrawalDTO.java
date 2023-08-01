package ua.vspelykh.atm.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class WithdrawalDTO extends AbstractDTO {
    private BigDecimal amount;
    private Timestamp transactionDate;
    private AccountDTO account;
    private ATMDTO atm;
}
