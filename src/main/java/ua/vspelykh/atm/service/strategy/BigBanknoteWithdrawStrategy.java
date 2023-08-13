package ua.vspelykh.atm.service.strategy;

import org.springframework.stereotype.Component;
import ua.vspelykh.atm.config.AtmIdHolder;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents a big banknote withdrawal strategy for an ATM system.
 * This strategy allows withdrawal of big banknotes (100, 200, 500) and can also use the small banknote strategy
 * to fill the remaining amount if needed.
 *
 * @version 1.0
 */
@Component
public class BigBanknoteWithdrawStrategy extends AbstractWithdrawStrategy {

    private static final int MIN_AMOUNT = 100;

    /**
     * Constructor for the BigBanknoteWithdrawStrategy class.
     *
     * @param atmIdHolder An AtmIdHolder instance representing the ATM's identifier holder.
     */
    public BigBanknoteWithdrawStrategy(AtmIdHolder atmIdHolder) {
        super(atmIdHolder);
    }

    /**
     * Method to withdraw the requested amount using the big banknote strategy.
     * If the withdrawal is not possible with the big banknotes, it tries to use small banknotes.
     *
     * @param amountToWithdraw   The amount to withdraw from the ATM.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return A list of BanknoteDTO representing the banknotes withdrawn, or an empty list if the withdrawal is not possible.
     */
    @Override
    public List<BanknoteDTO> withdraw(int amountToWithdraw, List<Banknote> availableBanknotes) {
        if (!isWithdrawPossible(amountToWithdraw, availableBanknotes)) {
            return Collections.emptyList();
        }
        return getBanknoteDTOS(amountToWithdraw, availableBanknotes);
    }

    /**
     * Private helper method to perform the banknote withdrawal based on the requested amount and available banknotes.
     * This method uses both big and small banknotes to fulfill the requested amount, if necessary.
     *
     * @param amountToWithdraw   The remaining amount to withdraw.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return A list of BanknoteDTO representing the withdrawal, or null if the withdrawal is not possible.
     */
    private List<BanknoteDTO> getBanknoteDTOS(int amountToWithdraw, List<Banknote> availableBanknotes) {
        List<BanknoteDTO> withdrawalBanknotes = new ArrayList<>();
        int remainingAmount = amountToWithdraw;

        remainingAmount = withdrawBanknotes(AVAILABLE_BIG_DENOMINATIONS, availableBanknotes, withdrawalBanknotes, remainingAmount);
        if (remainingAmount != 0) {
            remainingAmount = withdrawBanknotes(AVAILABLE_SMALL_DENOMINATIONS, availableBanknotes, withdrawalBanknotes, remainingAmount);
        }
        if (!isNumOfBanknotesValid(withdrawalBanknotes)) {
            return rollback(withdrawalBanknotes, availableBanknotes);
        }
        return remainingAmount == 0 ? withdrawalBanknotes : rollback(withdrawalBanknotes, availableBanknotes);
    }

    /**
     * Private helper method to withdraw banknotes of a specific denomination from the available banknotes.
     *
     * @param denominations       The denominations to use for withdrawal (big or small banknotes).
     * @param availableBanknotes  The list of available banknotes in the ATM.
     * @param withdrawalBanknotes A list to store the banknotes being withdrawn.
     * @param remainingAmount     The remaining amount to withdraw.
     * @return The remaining amount after withdrawal.
     */
    private int withdrawBanknotes(int[] denominations, List<Banknote> availableBanknotes, List<BanknoteDTO> withdrawalBanknotes,
                                  int remainingAmount) {
        for (int denomination : denominations) {
            int quantityToWithdraw = remainingAmount / denomination;
            if (quantityToWithdraw == 0) {
                continue;
            }
            Optional<Banknote> optionalBanknote = getIfPossibleToWithdraw(availableBanknotes, denomination, quantityToWithdraw);
            if (optionalBanknote.isPresent()) {
                withdrawBanknote(withdrawalBanknotes, optionalBanknote.get(), quantityToWithdraw);
                remainingAmount -= (quantityToWithdraw * denomination);
            } else {
                Optional<Banknote> optionalAllQuantityBanknote = getAllPossibleToWithdraw(availableBanknotes, denomination);
                if (optionalAllQuantityBanknote.isPresent()) {
                    int quantity = optionalAllQuantityBanknote.get().getQuantity();
                    withdrawBanknote(withdrawalBanknotes, optionalAllQuantityBanknote.get(), quantity);
                    remainingAmount -= (quantity * denomination);
                }
            }
            if (remainingAmount == 0) {
                return remainingAmount;
            }
        }
        return remainingAmount;
    }

    /**
     * Helper method to check if it's possible to withdraw all quantity of banknotes of a given denomination.
     *
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @param denomination       The denomination of the banknote to withdraw.
     * @return An Optional containing the Banknote to withdraw if possible, or an empty Optional if not possible.
     */
    private Optional<Banknote> getAllPossibleToWithdraw(List<Banknote> availableBanknotes, int denomination) {
        return availableBanknotes.stream()
                .filter(banknote -> banknote.getDenomination() == denomination)
                .findFirst();
    }

    /**
     * Checks if the requested amount is possible to withdraw based on the available banknotes.
     *
     * @param amountToWithdraw   The amount to withdraw from the ATM.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return True if the withdrawal is possible, false otherwise.
     */
    @Override
    public boolean isWithdrawPossible(int amountToWithdraw, List<Banknote> availableBanknotes) {
        if (amountToWithdraw < MIN_AMOUNT || amountToWithdraw > getSumOfBanknotes(availableBanknotes)) {
            return false;
        }
        List<BanknoteDTO> banknoteDTOS = getBanknoteDTOS(amountToWithdraw, availableBanknotes);
        boolean isPossible = !banknoteDTOS.isEmpty();
        rollback(banknoteDTOS, availableBanknotes);
        return isPossible;
    }

    /**
     * Checks if this strategy supports the given strategy type.
     *
     * @param strategyType The strategy type to check.
     * @return True if the strategy supports the given type, false otherwise.
     */
    @Override
    public boolean supportsStrategyType(StrategyType strategyType) {
        return strategyType == StrategyType.BIG;
    }

    @Override
    public StrategyType getStrategyType() {
        return StrategyType.BIG;
    }

    /**
     * Provides a string representation of the class.
     *
     * @return The class name as a string.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}