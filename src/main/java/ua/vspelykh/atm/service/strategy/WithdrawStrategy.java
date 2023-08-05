package ua.vspelykh.atm.service.strategy;

import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.List;

public interface WithdrawStrategy {

    List<BanknoteDTO> withdraw(int amountToWithdraw, List<Banknote> availableBanknotes);

    boolean isWithdrawPossible(int amountToWithdraw, List<Banknote> availableBanknotes);

}
