package ua.vspelykh.atm.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class WithdrawalDTO extends AbstractDTO {
    private Integer amount;
    private Timestamp transactionDate;
    private AccountDTO account;
    private ATMDTO atm;
}
