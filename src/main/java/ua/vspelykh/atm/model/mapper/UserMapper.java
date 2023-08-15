package ua.vspelykh.atm.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.vspelykh.atm.model.dto.UserDTO;
import ua.vspelykh.atm.model.entity.User;
import ua.vspelykh.atm.model.util.Columns;

import static ua.vspelykh.atm.model.mapper.BaseMapperConfig.SPRING;
import static ua.vspelykh.atm.model.util.Columns.*;

@Mapper(componentModel = SPRING,uses = BaseMapperConfig.class)
public interface UserMapper {

    @Mapping(target = Columns.ID, source = ENTITY_ID)
    @Mapping(target = FIRST_NAME, source = ENTITY_FIRST_NAME)
    @Mapping(target = LAST_NAME, source = ENTITY_LAST_NAME)
    @Mapping(target = PHONE_NUMBER, source = ENTITY_PHONE_NUMBER)
    @Mapping(target = ROLE, source = ENTITY_ROLE)
    UserDTO toDTO(User entity);

    @Mapping(target = ID, source = DTO_ID)
    @Mapping(target = FIRST_NAME, source = DTO_FIRST_NAME)
    @Mapping(target = LAST_NAME, source = DTO_LAST_NAME)
    @Mapping(target = PHONE_NUMBER, source = DTO_PHONE_NUMBER)
    @Mapping(target = ROLE, source = DTO_ROLE)
    User toEntity(UserDTO dto);
}
