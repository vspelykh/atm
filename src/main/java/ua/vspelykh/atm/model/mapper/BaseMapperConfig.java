package ua.vspelykh.atm.model.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

import static ua.vspelykh.atm.model.mapper.BaseMapperConfig.SPRING;

@MapperConfig(componentModel = SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseMapperConfig {

    public static final String SPRING = "spring";
}