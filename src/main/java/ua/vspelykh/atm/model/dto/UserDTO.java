package ua.vspelykh.atm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;

}
