package ua.vspelykh.atm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {
    private Integer id;
    private String accountNumber;
    private BigDecimal balance;
    private Integer userId;
    private String password;
    private LocalDate issueDate;
    private UserDTO user;
}
