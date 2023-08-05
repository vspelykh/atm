package ua.vspelykh.atm.service.strategy;

import org.junit.jupiter.api.Test;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ua.vspelykh.atm.service.strategy.StrategyTestData.fullBanknotes;

class MixedBanknotesWithdrawStrategyTest extends AbstractStrategyTest {

    WithdrawStrategy strategy = new MixedBanknotesWithdrawStrategy(atmIdHolder);

    @Test
    void test(){

        List<Banknote> banknotes = fullBanknotes();
        int amountToWithdraw = 2000;

        System.out.println(strategy.isWithdrawPossible(amountToWithdraw, banknotes));
        List<BanknoteDTO> withdraw = strategy.withdraw(amountToWithdraw, banknotes);
        System.out.println(withdraw);

        int sum = 0;
        for (BanknoteDTO banknoteDTO : withdraw){
            sum += banknoteDTO.getDenomination() * banknoteDTO.getQuantity();
        }
        System.out.println(sum);
    }
}