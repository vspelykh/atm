package ua.vspelykh.atm.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class UserDTO extends AbstractDTO {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;

}
