package ua.vspelykh.atm.service.strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ua.vspelykh.atm.service.strategy.StrategyTestData.*;
import static ua.vspelykh.atm.service.strategy.StrategyTestData.fullBanknotes;

class BigBanknoteWithdrawStrategyTest extends AbstractStrategyTest {

    WithdrawStrategy strategy = new BigBanknoteWithdrawStrategy(atmIdHolder);

    @Test
    void test(){

        List<Banknote> banknotes = fullBanknotes();
        int amountToWithdraw = 2890;

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