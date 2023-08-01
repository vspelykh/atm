package ua.vspelykh.atm.model.converter;

import org.springframework.stereotype.Component;
import ua.vspelykh.atm.model.dto.UserDTO;
import ua.vspelykh.atm.model.entity.User;

@Component
public class UserConverter {

    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .role(userDTO.getRole())
                .build();
    }
}