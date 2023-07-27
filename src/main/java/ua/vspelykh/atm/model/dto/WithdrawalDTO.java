package ua.vspelykh.atm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalDTO implements Serializable {
    private Integer id;
    private BigDecimal amount;
    private Timestamp transactionDate;
    private AccountDTO account;
    private ATMDTO atm;
}
