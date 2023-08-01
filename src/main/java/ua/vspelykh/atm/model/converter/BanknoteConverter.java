package ua.vspelykh.atm.model.converter;

import org.springframework.stereotype.Component;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

@Component
public class BanknoteConverter {

    public BanknoteDTO toDto(Banknote banknote) {
        return BanknoteDTO.builder()
                .id(banknote.getId())
                .atmId(banknote.getAtmId())
                .denomination(banknote.getDenomination())
                .quantity(banknote.getQuantity())
                .build();
    }

    public Banknote toEntity(BanknoteDTO banknoteDTO) {
        return Banknote.builder()
                .atmId(banknoteDTO.getAtmId())
                .denomination(banknoteDTO.getDenomination())
                .quantity(banknoteDTO.getQuantity())
                .build();
    }
}