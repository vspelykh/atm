package ua.vspelykh.atm.service.strategy;

import lombok.RequiredArgsConstructor;
import ua.vspelykh.atm.config.AtmIdHolder;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractWithdrawStrategy implements WithdrawStrategy {

    private final AtmIdHolder atmIdHolder;

    protected static final int DENOMINATION_10 = 10;
    protected static final int DENOMINATION_20 = 20;
    protected static final int DENOMINATION_50 = 50;
    protected static final int DENOMINATION_100 = 100;
    protected static final int DENOMINATION_200 = 200;
    protected static final int DENOMINATION_500 = 500;

    protected static final int[] AVAILABLE_SMALL_DENOMINATIONS = {DENOMINATION_50, DENOMINATION_20, DENOMINATION_10};
    protected static final int[] AVAILABLE_BIG_DENOMINATIONS = {DENOMINATION_500, DENOMINATION_200, DENOMINATION_100};

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

    protected boolean isAmountCorrect(List<BanknoteDTO> banknotesForWithdraw, int amountToWithdraw) {
        return banknotesForWithdraw.stream()
                .mapToInt(banknote -> banknote.getDenomination() * banknote.getQuantity())
                .sum() == amountToWithdraw;
    }

    protected List<BanknoteDTO> rollback(List<BanknoteDTO> withdrawalBanknotes, List<Banknote> availableBanknotes) {
        for (BanknoteDTO banknoteDTO : withdrawalBanknotes) {
            Optional<Banknote> optionalBanknote = availableBanknotes.stream()
                    .filter(banknote -> banknote.getDenomination() == banknoteDTO.getDenomination()).findFirst();
            optionalBanknote.ifPresent(banknote -> rollbackBanknote(banknote, banknoteDTO.getQuantity()));
        }
        return Collections.emptyList();
    }

    protected void rollbackBanknote(Banknote banknote, int supplyQuantity) {
        banknote.setQuantity(banknote.getQuantity() + supplyQuantity);
    }

    public AtmIdHolder getAtmIdHolder() {
        return atmIdHolder;
    }
}
