package ua.vspelykh.atm.service.strategy;

import lombok.RequiredArgsConstructor;
import ua.vspelykh.atm.config.AtmIdHolder;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * AbstractWithdrawStrategy is an abstract class that provides common functionality for various withdrawal strategies.
 * It implements the WithdrawStrategy interface and defines helper methods to perform banknote withdrawal and rollback operations.
 *
 * @version 1.0
 */
@RequiredArgsConstructor
public abstract class AbstractWithdrawStrategy implements WithdrawStrategy {

    // The holder for ATM ID information
    private final AtmIdHolder atmIdHolder;

    // Constants for banknote quantities
    protected static final int QUANTITY_1 = 1;
    protected static final int QUANTITY_2 = 2;

    // Maximum number of banknotes allowed for a withdrawal
    private static final int MAX_AMOUNT_OF_BANKNOTES = 40;

    // Constants for banknote denominations
    protected static final int DENOMINATION_10 = 10;
    protected static final int DENOMINATION_20 = 20;
    protected static final int DENOMINATION_50 = 50;
    protected static final int DENOMINATION_100 = 100;
    protected static final int DENOMINATION_200 = 200;
    protected static final int DENOMINATION_500 = 500;

    // Arrays of available banknote denominations for different strategies
    protected static final int[] AVAILABLE_SMALL_DENOMINATIONS = {DENOMINATION_50, DENOMINATION_20, DENOMINATION_10};
    protected static final int[] AVAILABLE_BIG_DENOMINATIONS = {DENOMINATION_500, DENOMINATION_200, DENOMINATION_100};
    protected static final int[] AVAILABLE_MIXED_DENOMINATIONS = {DENOMINATION_200, DENOMINATION_100,
            DENOMINATION_50, DENOMINATION_20, DENOMINATION_10};
    protected static final int[] FILLABLE_MIXED_DENOMINATIONS = {DENOMINATION_200, DENOMINATION_100,
            DENOMINATION_50, DENOMINATION_20, DENOMINATION_10};

    /**
     * Helper method to check if it's possible to withdraw the specified quantity of banknotes of a given denomination.
     *
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @param denomination       The denomination of the banknote to withdraw.
     * @param quantity           The quantity of banknotes to withdraw.
     * @return An Optional containing the Banknote to withdraw if possible, or an empty Optional if not possible.
     */
    protected Optional<Banknote> getIfPossibleToWithdraw(List<Banknote> availableBanknotes, int denomination, int quantity) {
        Optional<Banknote> optionalBanknote = availableBanknotes.stream()
                .filter(banknote -> banknote.getDenomination() == denomination)
                .findFirst();
        if (optionalBanknote.isPresent()) {
            Banknote banknoteInATM = optionalBanknote.get();
            if (banknoteInATM.getQuantity() >= quantity) {
                return Optional.of(banknoteInATM);
            }
        }
        return Optional.empty();
    }

    /**
     * Helper method to perform the withdrawal of a banknote and update the available banknotes accordingly.
     *
     * @param withdrawalBanknotes The list of banknotes to be withdrawn.
     * @param banknoteInATM       The Banknote to withdraw.
     * @param quantity            The quantity of banknotes to withdraw.
     */
    protected void withdrawBanknote(List<BanknoteDTO> withdrawalBanknotes, Banknote banknoteInATM, int quantity) {
        Optional<BanknoteDTO> banknoteForWithdraw = withdrawalBanknotes.stream()
                .filter(banknoteDTO -> banknoteDTO.getDenomination() == banknoteInATM.getDenomination())
                .findFirst();
        if (banknoteForWithdraw.isPresent()) {
            BanknoteDTO banknoteDTO = banknoteForWithdraw.get();
            banknoteDTO.setQuantity(banknoteDTO.getQuantity() + quantity);
        } else {
            withdrawalBanknotes.add(new BanknoteDTO(getAtmIdHolder().getAtmId(), banknoteInATM.getDenomination(), quantity));
        }
        banknoteInATM.setQuantity(banknoteInATM.getQuantity() - quantity);
    }

    /**
     * Helper method to check if the total amount of banknotes in the withdrawal matches the requested amount.
     *
     * @param banknotesForWithdraw The list of BanknoteDTO representing the withdrawal.
     * @param amountToWithdraw     The requested amount to withdraw.
     * @return True if the total amount of banknotes matches the requested amount, false otherwise.
     */
    protected boolean isAmountIncorrect(List<BanknoteDTO> banknotesForWithdraw, int amountToWithdraw) {
        return getSumOfBanknotesDTO(banknotesForWithdraw) != amountToWithdraw;
    }

    /**
     * Helper method to calculate the total amount of banknotes in a list of BanknoteDTO.
     *
     * @param banknotesForWithdraw The list of BanknoteDTO.
     * @return The total amount of banknotes in the list.
     */
    protected int getSumOfBanknotesDTO(List<BanknoteDTO> banknotesForWithdraw) {
        return banknotesForWithdraw.stream()
                .mapToInt(banknote -> banknote.getDenomination() * banknote.getQuantity())
                .sum();
    }

    /**
     * Helper method to calculate the total amount of banknotes in a list of Banknote.
     *
     * @param banknotesForWithdraw The list of Banknote.
     * @return The total amount of banknotes in the list.
     */
    protected int getSumOfBanknotes(List<Banknote> banknotesForWithdraw) {
        return banknotesForWithdraw.stream()
                .mapToInt(banknote -> banknote.getDenomination() * banknote.getQuantity())
                .sum();
    }

    /**
     * Helper method to roll back a withdrawal and restore the original banknote quantities.
     *
     * @param withdrawalBanknotes The list of BanknoteDTO representing the withdrawal.
     * @param availableBanknotes  The list of available banknotes in the ATM.
     * @return An empty list since the rollback operation does not involve returning banknotes.
     */
    protected List<BanknoteDTO> rollback(List<BanknoteDTO> withdrawalBanknotes, List<Banknote> availableBanknotes) {
        for (BanknoteDTO banknoteDTO : withdrawalBanknotes) {
            Optional<Banknote> optionalBanknote = availableBanknotes.stream()
                    .filter(banknote -> banknote.getDenomination() == banknoteDTO.getDenomination()).findFirst();
            optionalBanknote.ifPresent(banknote -> rollbackBanknote(banknote, banknoteDTO.getQuantity()));
        }
        return Collections.emptyList();
    }

    /**
     * Helper method to roll back a single banknote and restore its quantity.
     *
     * @param banknote       The Banknote to rollback.
     * @param supplyQuantity The quantity of banknotes to restore.
     */
    protected void rollbackBanknote(Banknote banknote, int supplyQuantity) {
        banknote.setQuantity(banknote.getQuantity() + supplyQuantity);
    }

    /**
     * Helper method to check if the total number of banknotes in a withdrawal is within the allowed limit.
     *
     * @param withdraw The list of BanknoteDTO representing the withdrawal.
     * @return True if the total number of banknotes is within the allowed limit, false otherwise.
     */
    protected boolean isNumOfBanknotesValid(List<BanknoteDTO> withdraw) {
        return withdraw.stream().mapToInt(BanknoteDTO::getQuantity).sum() <= MAX_AMOUNT_OF_BANKNOTES;
    }

    /**
     * Helper method to withdraw banknotes of denomination 20s and 10s.
     *
     * @param availableBanknotes  The list of available banknotes in the ATM.
     * @param withdrawalBanknotes The list of banknotes to be withdrawn.
     * @param remainingAmount     The remaining amount to withdraw.
     * @return The updated remaining amount after withdrawing 50s.
     */
    protected int withdraw20sAnd10(List<Banknote> availableBanknotes, List<BanknoteDTO> withdrawalBanknotes, int remainingAmount) {
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
     * Helper method to withdraw the remaining amount using 20s and 10s banknotes.
     *
     * @param denominations      The array of denominations to use for the remaining amount withdrawal.
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @param withdrawalBanknotes The list of banknotes to be withdrawn.
     * @param remainingAmount     The remaining amount to withdraw.
     * @return The updated remaining amount after withdrawing the remaining banknotes.
     */
    protected int withdrawRemaining(int[] denominations, List<Banknote> availableBanknotes,
                                    List<BanknoteDTO> withdrawalBanknotes, int remainingAmount) {
        for (int denomination : denominations) {
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
     * Helper method to get a banknote from the available banknotes list for verification.
     *
     * @param availableBanknotes The list of available banknotes in the ATM.
     * @param denomination       The denomination of the banknote to verify.
     * @return An Optional containing the Banknote for verification if present, or an empty Optional if not found.
     */
    protected Optional<Banknote> getBanknoteForVerify(List<Banknote> availableBanknotes, int denomination) {
        return availableBanknotes.stream()
                .filter(banknote -> banknote.getDenomination() == denomination)
                .findFirst();
    }

    /**
     * Helper method to check if the ATM has a banknote (optionalBanknote is present).
     *
     * @param optionalBanknote An Optional containing the Banknote to check.
     * @return True if the ATM has the banknote, false otherwise.
     */
    protected boolean isATMHasBanknote(boolean optionalBanknote) {
        return optionalBanknote;
    }

    /**
     * Getter for the ATM ID holder.
     *
     * @return The AtmIdHolder instance.
     */
    public AtmIdHolder getAtmIdHolder() {
        return atmIdHolder;
    }
}