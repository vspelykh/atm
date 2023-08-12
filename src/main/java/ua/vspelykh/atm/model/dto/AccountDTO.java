package ua.vspelykh.atm.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class AccountDTO extends AbstractDTO {
    private String accountNumber;
    private Integer balance;
    private Integer userId;
    private String password;
    private LocalDate issueDate;
    private UserDTO user;
}
