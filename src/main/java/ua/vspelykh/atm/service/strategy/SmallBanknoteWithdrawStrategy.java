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
 * SmallBanknoteWithdrawStrategy is an implementation of the WithdrawStrategy interface for a small ATM.
 * It allows withdrawing money using a combination of 50s, 20s, and 10s banknotes.
 * The maximum amount that can be withdrawn is 400 units.
 * The strategy aims to minimize the number of banknotes used for the withdrawal.
 * and the remaining amount is covered by 20s and 10s banknotes.
 *
 * @version 1.0
 */
@Component
public class SmallBanknoteWithdrawStrategy extends AbstractWithdrawStrategy {

    private static final int MIN_AMOUNT = 50;
    private static final int MAX_AMOUNT = 400;

    /**
     * Creates a new instance of SmallBanknoteWithdrawStrategy.
     *
     * @param atmIdHolder The instance of AtmIdHolder to be used for identifying the ATM.
     */
    public SmallBanknoteWithdrawStrategy(AtmIdHolder atmIdHolder) {
        super(atmIdHolder);
    }

    /**
     * Withdraws the requested amount from the available banknotes.
     *
     * @param amountToWithdraw   The amount to withdraw.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return A list of BanknoteDTO representing the withdrawal.
     * If withdrawal is not possible, returns an empty list.
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
     *
     * @param amountToWithdraw   The remaining amount to withdraw.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return A list of BanknoteDTO representing the withdrawal.
     */
    private List<BanknoteDTO> getBanknoteDTOS(int amountToWithdraw, List<Banknote> availableBanknotes) {
        List<BanknoteDTO> withdrawalBanknotes = new ArrayList<>();
        int remainingAmount = amountToWithdraw;

        // Withdraw 20s and 10s first for amounts less than 50
        remainingAmount = withdraw20sAnd10(availableBanknotes, withdrawalBanknotes, remainingAmount);

        // Withdraw 50s for the remaining amount (if available)
        remainingAmount = withdraw50s(availableBanknotes, withdrawalBanknotes, remainingAmount);

        // Withdraw the remaining amount using mixed denominations
        if (remainingAmount != 0) {
            remainingAmount = withdrawRemaining(AVAILABLE_SMALL_DENOMINATIONS, availableBanknotes, withdrawalBanknotes,
                    remainingAmount);
        }

        return remainingAmount == 0 ? withdrawalBanknotes : rollback(withdrawalBanknotes, availableBanknotes);
    }

    /**
     * Private helper method to withdraw banknotes of denomination 50s.
     *
     * @param availableBanknotes  The list of available banknotes in the ATM.
     * @param withdrawalBanknotes The list of banknotes to be withdrawn.
     * @param remainingAmount     The remaining amount to withdraw.
     * @return The updated remaining amount after withdrawing 50s.
     */
    private int withdraw50s(List<Banknote> availableBanknotes, List<BanknoteDTO> withdrawalBanknotes, int remainingAmount) {
        int quantityToWithdraw = remainingAmount / DENOMINATION_50;
        Optional<Banknote> optionalBanknote = getIfPossibleToWithdraw(availableBanknotes, DENOMINATION_50, quantityToWithdraw);
        if (optionalBanknote.isPresent()) {
            withdrawBanknote(withdrawalBanknotes, optionalBanknote.get(), quantityToWithdraw);
            return remainingAmount - (quantityToWithdraw * DENOMINATION_50);
        }
        return remainingAmount;
    }

    /**
     * Checks if the withdrawal of the requested amount is possible using the available banknotes.
     *
     * @param amountToWithdraw   The amount to withdraw.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return True if the withdrawal is possible, false otherwise.
     */
    @Override
    public boolean isWithdrawPossible(int amountToWithdraw, List<Banknote> availableBanknotes) {
        if (amountToWithdraw > MAX_AMOUNT || amountToWithdraw < MIN_AMOUNT) {
            return false;
        }
        Optional<Banknote> optionalBanknote = getBanknoteForVerify(availableBanknotes, DENOMINATION_50);
        if (isATMHasBanknote(optionalBanknote.isPresent() && optionalBanknote.get().getQuantity() == 0)) {
            return false;
        }
        return mockWithdrawAndRollback(amountToWithdraw, availableBanknotes);
    }

    /**
     * Checks if this strategy supports the given strategy type.
     *
     * @param strategyType The strategy type to check.
     * @return True if the strategy supports the given type, false otherwise.
     */
    @Override
    public boolean supportsStrategyType(StrategyType strategyType) {
        return strategyType == StrategyType.SMALL;
    }

    @Override
    public StrategyType getStrategyType() {
        return StrategyType.SMALL;
    }

    /**
     * Private helper method to mock the withdrawal of the requested amount using available banknotes
     * and then rolls back the changes.
     *
     * @param amountToWithdraw   The amount to withdraw.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @return true if the withdrawal was successful, otherwise false.
     */
    private boolean mockWithdrawAndRollback(int amountToWithdraw, List<Banknote> availableBanknotes) {
        List<BanknoteDTO> banknotesForWithdraw = getBanknoteDTOS(amountToWithdraw, availableBanknotes);
        if (banknotesForWithdraw.isEmpty() || isAmountIncorrect(banknotesForWithdraw, amountToWithdraw)) {
            rollback(banknotesForWithdraw, availableBanknotes);
            return false;
        } else {
            rollback(banknotesForWithdraw, availableBanknotes);
            return true;
        }
    }

    /**
     * Returns the name of the strategy.
     *
     * @return The name of the strategy.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}