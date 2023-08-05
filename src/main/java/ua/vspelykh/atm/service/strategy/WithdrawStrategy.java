package ua.vspelykh.atm.service.strategy;

import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.List;

/**
 * The WithdrawStrategy interface defines the contract for various strategies to perform banknote withdrawals
 * in an ATM system. Different implementations of this interface can allow withdrawals in different ways, such as
 * using specific denominations or following certain rules.
 *
 * @version 1.0
 */
public interface WithdrawStrategy {

    /**
     * Performs the withdrawal of the requested amount from the available banknotes.
     *
     * @param amountToWithdraw   The amount to withdraw.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return A list of BanknoteDTO representing the withdrawal.
     * If withdrawal is not possible, returns an empty list.
     */
    List<BanknoteDTO> withdraw(int amountToWithdraw, List<Banknote> availableBanknotes);

    /**
     * Checks if the withdrawal of the requested amount is possible using the available banknotes.
     *
     * @param amountToWithdraw   The amount to withdraw.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return True if the withdrawal is possible, false otherwise.
     */
    boolean isWithdrawPossible(int amountToWithdraw, List<Banknote> availableBanknotes);
}

