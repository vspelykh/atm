package ua.vspelykh.atm.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private Double balance;
    private Integer userId;
    private String password;
    private LocalDate issueDate;
    private UserDTO user;
}
