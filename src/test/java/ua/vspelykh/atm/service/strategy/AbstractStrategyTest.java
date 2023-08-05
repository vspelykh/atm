package ua.vspelykh.atm.service.strategy;

import ua.vspelykh.atm.config.AtmIdHolder;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.List;

import static ua.vspelykh.atm.service.strategy.StrategyTestData.ATM_ID;

abstract class AbstractStrategyTest {

    protected AtmIdHolder atmIdHolder = new AtmIdHolder(ATM_ID);

    protected int getAmountFromList(List<Banknote> banknotes){
        int amount = 0;
        for (Banknote banknote : banknotes){
            amount += banknote.getDenomination() * banknote.getQuantity();
        }
        return amount;
    }

    protected int getAmountFromListDTO(List<BanknoteDTO> banknotes){
        int amount = 0;
        for (BanknoteDTO banknote : banknotes){
            amount += banknote.getDenomination() * banknote.getQuantity();
        }
        return amount;
    }
}
