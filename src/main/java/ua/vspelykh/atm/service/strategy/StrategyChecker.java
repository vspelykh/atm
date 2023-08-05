package ua.vspelykh.atm.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.ArrayList;
import java.util.List;


/**
 * A utility class to check and retrieve the allowed withdrawal strategies for a given withdrawal amount
 * and the available banknotes in the ATM.
 *
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class StrategyChecker {

    private final WithdrawStrategy smallBanknoteWithdrawStrategy;
    private final WithdrawStrategy bigBanknoteWithdrawStrategy;
    private final WithdrawStrategy mixedBanknotesWithdrawStrategy;

    /**
     * Checks and retrieves the allowed withdrawal strategies for a given withdrawal amount and the available banknotes.
     *
     * @param amountToWithdraw   The amount to be withdrawn.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return A list of allowed withdrawal strategies.
     */
    public List<WithdrawStrategy> getAllowedStrategies(int amountToWithdraw, List<Banknote> availableBanknotes) {
        List<WithdrawStrategy> allowedStrategies = new ArrayList<>();

        if (smallBanknoteWithdrawStrategy.isWithdrawPossible(amountToWithdraw, availableBanknotes)) {
            allowedStrategies.add(smallBanknoteWithdrawStrategy);
        }

        if (bigBanknoteWithdrawStrategy.isWithdrawPossible(amountToWithdraw, availableBanknotes)) {
            allowedStrategies.add(bigBanknoteWithdrawStrategy);
        }

        if (mixedBanknotesWithdrawStrategy.isWithdrawPossible(amountToWithdraw, availableBanknotes)) {
            allowedStrategies.add(mixedBanknotesWithdrawStrategy);
        }

        return allowedStrategies;
    }
}