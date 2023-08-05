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
 * Represents a mixed banknote withdrawal strategy for an ATM system.
 * This strategy allows withdrawal of various banknotes (10, 20, 50, 100, 200, 500) in a specific manner.
 *
 * @version 1.0
 */
@Component
public class MixedBanknotesWithdrawStrategy extends AbstractWithdrawStrategy {

    private static final int MIN_AMOUNT = 500;
    private static final int MAX_AMOUNT = 2000;

    /**
     * Creates a new instance of the MixedBanknotesWithdrawStrategy with the given ATM ID holder.
     *
     * @param atmIdHolder The holder for ATM ID information.
     */
    public MixedBanknotesWithdrawStrategy(AtmIdHolder atmIdHolder) {
        super(atmIdHolder);
    }

    /**
     * Withdraws the specified amount using mixed banknotes.
     *
     * @param amountToWithdraw    The amount to be withdrawn.
     * @param availableBanknotes  The list of available banknotes in the ATM.
     * @return A list of BanknoteDTO representing the withdrawn banknotes, or an empty list if withdrawal is not possible.
     */
    @Override
    public List<BanknoteDTO> withdraw(int amountToWithdraw, List<Banknote> availableBanknotes) {
        if (!isWithdrawPossible(amountToWithdraw, availableBanknotes)) {
            return Collections.emptyList();
        }
        return getBanknotesDTOS(amountToWithdraw, availableBanknotes);
    }

    /**
     * Checks if the withdrawal of the specified amount is possible using mixed banknotes.
     *
     * @param amountToWithdraw    The amount to be withdrawn.
     * @param availableBanknotes  The list of available banknotes in the ATM.
     * @return true if the withdrawal is possible, otherwise false.
     */
    @Override
    public boolean isWithdrawPossible(int amountToWithdraw, List<Banknote> availableBanknotes) {
        if (isATMHasBanknote(amountToWithdraw < MIN_AMOUNT || amountToWithdraw > MAX_AMOUNT)) {
            return false;
        }
        for (int denomination : AVAILABLE_MIXED_DENOMINATIONS) {
            Optional<Banknote> optionalBanknote = getBanknoteForVerify(availableBanknotes, denomination);
            if (isATMHasBanknote(optionalBanknote.isPresent() && optionalBanknote.get().getQuantity() == 0)) {
                return false;
            }
        }
        return mockWithdrawAndRollback(amountToWithdraw, availableBanknotes);
    }

    /**
     * Mocks the withdrawal of the specified amount using mixed banknotes and then rolls back the changes.
     *
     * @param amountToWithdraw    The amount to be withdrawn.
     * @param availableBanknotes  The list of available banknotes in the ATM.
     * @return true if the withdrawal was successful, otherwise false.
     */
    protected boolean mockWithdrawAndRollback(int amountToWithdraw, List<Banknote> availableBanknotes) {
        List<BanknoteDTO> banknotesForWithdraw = getBanknotesDTOS(amountToWithdraw, availableBanknotes);
        if (banknotesForWithdraw.isEmpty() || isAmountIncorrect(banknotesForWithdraw, amountToWithdraw)) {
            rollback(banknotesForWithdraw, availableBanknotes);
            return false;
        } else {
            rollback(banknotesForWithdraw, availableBanknotes);
            return true;
        }
    }

    /**
     * Gets the list of BanknoteDTO representing the withdrawn banknotes using mixed banknotes.
     *
     * @param amountToWithdraw    The amount to be withdrawn.
     * @param availableBanknotes  The list of available banknotes in the ATM.
     * @return A list of BanknoteDTO representing the withdrawn banknotes.
     */
    private List<BanknoteDTO> getBanknotesDTOS(int amountToWithdraw, List<Banknote> availableBanknotes) {
        List<BanknoteDTO> withdrawalBanknotes = new ArrayList<>();
        int remainingAmount = amountToWithdraw;

        // Withdraw 500 banknotes first for amounts >= 1000
        if (remainingAmount >= 1000) {
            Optional<Banknote> optionalBanknote = getIfPossibleToWithdraw(availableBanknotes, DENOMINATION_500, QUANTITY_1);
            if (optionalBanknote.isPresent()) {
                withdrawBanknote(withdrawalBanknotes, optionalBanknote.get(), QUANTITY_1);
                remainingAmount -= (QUANTITY_1 * DENOMINATION_500);
            }
        }

        // Withdraw 20s and 10s for amounts < 1000
        for (int i = 0; i < 2; i++) {
            remainingAmount = withdraw20sAnd10(availableBanknotes, withdrawalBanknotes, remainingAmount);
        }

        // Withdraw 200 banknotes for amounts >= 500
        if (remainingAmount >= 500) {
            Optional<Banknote> optionalBanknote = getIfPossibleToWithdraw(availableBanknotes, DENOMINATION_200, QUANTITY_1);
            if (optionalBanknote.isPresent()) {
                withdrawBanknote(withdrawalBanknotes, optionalBanknote.get(), QUANTITY_1);
                remainingAmount -= (QUANTITY_1 * DENOMINATION_200);
            }
        }

        // Withdraw 100 banknotes for amounts >= 300
        if (remainingAmount >= 300) {
            Optional<Banknote> optionalBanknote = getIfPossibleToWithdraw(availableBanknotes, DENOMINATION_100, QUANTITY_2);
            if (optionalBanknote.isPresent()) {
                withdrawBanknote(withdrawalBanknotes, optionalBanknote.get(), QUANTITY_2);
                remainingAmount -= (QUANTITY_2 * DENOMINATION_100);
            }
        }

        // Withdraw the remaining amount using mixed denominations
        if (remainingAmount != 0) {
            remainingAmount = withdrawRemaining(AVAILABLE_MIXED_DENOMINATIONS, availableBanknotes, withdrawalBanknotes,
                    remainingAmount);
        }

        return remainingAmount == 0 ? withdrawalBanknotes : rollback(withdrawalBanknotes, availableBanknotes);
    }

    /**
     * Returns the name of this withdrawal strategy.
     *
     * @return The name of the strategy.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}