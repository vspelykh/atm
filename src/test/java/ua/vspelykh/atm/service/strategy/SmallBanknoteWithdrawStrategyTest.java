package ua.vspelykh.atm.service.strategy;

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

class SmallBanknoteWithdrawStrategyTest extends AbstractStrategyTest {

    private static final int SMALL_AMOUNT = 100;
    private static final int MAX_AMOUNT = 400;
    private static final int FORBIDDEN_AMOUNT = 500;

    private final WithdrawStrategy strategy = new SmallBanknoteWithdrawStrategy(atmIdHolder);

    @Test
    void checkATMIfAmountIsForbidden() {
        List<Banknote> expectedInATM = List.copyOf(fullBanknotes());
        List<Banknote> leftInATM = fullBanknotes();
        List<BanknoteDTO> actualWithdraw = strategy.withdraw(FORBIDDEN_AMOUNT, leftInATM);
        actualWithdraw.sort(Comparator.comparingInt(BanknoteDTO::getDenomination));
        assertEquals(0, getAmountFromListDTO(actualWithdraw));
        assertEquals(expectedInATM, leftInATM);
        assertEquals(Collections.emptyList(), actualWithdraw);
    }

    @ParameterizedTest
    @MethodSource("dataForWithdrawTest")
    void withdrawValidCheckBeforeAndAfter(int amount, List<Banknote> availableBanknotes, List<Banknote> leftInATM,
                                          List<BanknoteDTO> withdraw) {
        List<BanknoteDTO> actualWithdraw = strategy.withdraw(amount, availableBanknotes);
        actualWithdraw.sort(Comparator.comparingInt(BanknoteDTO::getDenomination));
        assertEquals(amount, getAmountFromListDTO(withdraw));
        assertEquals(leftInATM, availableBanknotes);
        assertEquals(withdraw, actualWithdraw);
    }

    @Parameterized.Parameters
    private static Collection<Object[]> dataForWithdrawTest() {
        return Arrays.asList(new Object[][]{
                {SMALL_AMOUNT, lowSmallBanknotes3(), smallStrategyRes1(), resDTO1()},
                {MAX_AMOUNT, fullBanknotes(), smallStrategyRes2(), resDTO2()},
                {MAX_AMOUNT, low50s(), smallStrategyRes3(), resDTO3()}}
        );
    }

    @Test
    void withdrawValid() {
        assertTrue(strategy.isWithdrawPossible(SMALL_AMOUNT, fullBanknotes()));
        assertTrue(strategy.isWithdrawPossible(MAX_AMOUNT, fullBanknotes()));
        assertTrue(strategy.isWithdrawPossible(SMALL_AMOUNT, lowSmallBanknotes3()));
    }

    @Test
    void withdrawForbiddenAmount() {
        assertFalse(strategy.isWithdrawPossible(FORBIDDEN_AMOUNT, fullBanknotes()));
        assertEquals(Collections.emptyList(), strategy.withdraw(FORBIDDEN_AMOUNT, fullBanknotes()));
    }

    @Test
    void withdrawEmpty50s() {
        assertFalse(strategy.isWithdrawPossible(MAX_AMOUNT, empty50s()));
        assertEquals(Collections.emptyList(), strategy.withdraw(MAX_AMOUNT, empty50s()));
    }

    @Test
    void withdrawNotEnoughSmallBanknotes() {
        assertFalse(strategy.isWithdrawPossible(MAX_AMOUNT, lowSmallBanknotes()));
        assertEquals(Collections.emptyList(), strategy.withdraw(MAX_AMOUNT, lowSmallBanknotes()));

        assertFalse(strategy.isWithdrawPossible(MAX_AMOUNT, lowSmallBanknotes2()));
        assertEquals(Collections.emptyList(), strategy.withdraw(MAX_AMOUNT, lowSmallBanknotes2()));
    }

    @ParameterizedTest()
    @MethodSource("dataForTestRollback")
    void testRollback(int amount) {
        List<Banknote> banknotes = lowSmallBanknotes2();
        int amountBefore = 0;
        for (Banknote banknote : banknotes) {
            amountBefore += banknote.getDenomination() * banknote.getQuantity();
        }
        assertFalse(strategy.isWithdrawPossible(amount, banknotes));
        assertEquals(Collections.emptyList(), strategy.withdraw(amount, banknotes));
        int amountAfter = 0;
        for (Banknote banknote : banknotes) {
            amountAfter += banknote.getDenomination() * banknote.getQuantity();
        }
        assertEquals(amountBefore, amountAfter);
    }

    @Parameterized.Parameters
    private static Collection<Integer> dataForTestRollback() {
        return Arrays.asList(0, SMALL_AMOUNT, MAX_AMOUNT);
    }

}