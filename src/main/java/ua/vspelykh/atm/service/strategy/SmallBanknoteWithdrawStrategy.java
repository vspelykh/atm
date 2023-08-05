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
 * The strategy ensures that at least half of the withdrawal amount is covered by 50s banknotes,
 * and the remaining amount is covered by 20s and 10s banknotes.
 *
 * @version 1.0
 */
@Component
public class SmallBanknoteWithdrawStrategy extends AbstractWithdrawStrategy {

    private static final int MAX_AMOUNT = 400;
    private static final int QUANTITY_1 = 1;
    private static final int QUANTITY_2 = 2;

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
     *         If withdrawal is not possible, returns an empty list.
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

        remainingAmount = withdraw20sAnd10(availableBanknotes, withdrawalBanknotes, remainingAmount);
        remainingAmount = withdraw50s(availableBanknotes, withdrawalBanknotes, remainingAmount);
        if (remainingAmount != 0) {
            remainingAmount = withdrawRemaining(availableBanknotes, withdrawalBanknotes, remainingAmount);
        }

        return remainingAmount == 0 ? withdrawalBanknotes : rollback(withdrawalBanknotes, availableBanknotes);
    }

    /**
     * Private helper method to withdraw banknotes of denomination 20s and 10s.
     *
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @param withdrawalBanknotes The list of banknotes to be withdrawn.
     * @param remainingAmount    The remaining amount to withdraw.
     * @return The updated remaining amount after withdrawing 50s.
     */
    private int withdraw20sAnd10(List<Banknote> availableBanknotes, List<BanknoteDTO> withdrawalBanknotes, int remainingAmount) {
        if (getIfPossibleToWithdraw(availableBanknotes, DENOMINATION_20, QUANTITY_2).isPresent()
                && getIfPossibleToWithdraw(availableBanknotes, DENOMINATION_10, QUANTITY_1).isPresent()) {
            Optional<Banknote> optionalBanknote = getIfPossibleToWithdraw(availableBanknotes, DENOMINATION_20, QUANTITY_2);
            optionalBanknote.ifPresent(banknote -> withdrawBanknote(withdrawalBanknotes, banknote, QUANTITY_2));
            Optional<Banknote> optionalBanknote2 = getIfPossibleToWithdraw(availableBanknotes, DENOMINATION_10, QUANTITY_1);
            optionalBanknote2.ifPresent(banknote -> withdrawBanknote(withdrawalBanknotes, banknote, QUANTITY_1));
        }
        return remainingAmount - DENOMINATION_50;
    }

    /**
     * Private helper method to withdraw banknotes of denomination 50s.
     *
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @param withdrawalBanknotes The list of banknotes to be withdrawn.
     * @param remainingAmount    The remaining amount to withdraw.
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
     * Private helper method to withdraw the remaining amount using 20s and 10s banknotes.
     *
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @param withdrawalBanknotes The list of banknotes to be withdrawn.
     * @param remainingAmount    The remaining amount to withdraw.
     * @return The updated remaining amount after withdrawing the remaining banknotes.
     */
    private int withdrawRemaining(List<Banknote> availableBanknotes, List<BanknoteDTO> withdrawalBanknotes, int remainingAmount) {
        for (int denomination : AVAILABLE_SMALL_DENOMINATIONS) {
            do {
                Optional<Banknote> optionalBanknote = getIfPossibleToWithdraw(availableBanknotes, denomination, QUANTITY_1);
                if (optionalBanknote.isPresent() && remainingAmount >= denomination) {
                    withdrawBanknote(withdrawalBanknotes, optionalBanknote.get(), QUANTITY_1);
                    remainingAmount -= denomination;
                } else break;
            } while (true);
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
        if (amountToWithdraw > MAX_AMOUNT) {
            return false;
        }
        Optional<Banknote> optionalBanknote = availableBanknotes.stream()
                .filter(banknote -> banknote.getDenomination() == DENOMINATION_50)
                .findFirst();
        if (optionalBanknote.isPresent() && optionalBanknote.get().getQuantity() == 0) {
            return false;
        }
        List<BanknoteDTO> banknotesForWithdraw = getBanknoteDTOS(amountToWithdraw, availableBanknotes);
        if (banknotesForWithdraw.isEmpty() || !isAmountCorrect(banknotesForWithdraw, amountToWithdraw)) {
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
