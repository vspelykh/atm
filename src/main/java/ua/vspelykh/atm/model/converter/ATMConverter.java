package ua.vspelykh.atm.model.converter;

import org.springframework.stereotype.Component;
import ua.vspelykh.atm.model.dto.ATMDTO;
import ua.vspelykh.atm.model.entity.ATM;

@Component
public class ATMConverter {

    public ATMDTO toDto(ATM atm) {
        return ATMDTO.builder()
                .id(atm.getId())
                .location(atm.getLocation())
                .availability(atm.getAvailability())
                .build();
    }

    public ATM toEntity(ATMDTO atmDTO) {
        return ATM.builder()
                .location(atmDTO.getLocation())
                .availability(atmDTO.getAvailability())
                .build();
    }
}