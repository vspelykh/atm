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

class MixedBanknotesWithdrawStrategyTest extends AbstractStrategyTest {

    WithdrawStrategy strategy = new MixedBanknotesWithdrawStrategy(atmIdHolder);

    private static final int SMALL_AMOUNT = 400;
    private static final int MAX_AMOUNT = 2000;
    private static final int FORBIDDEN_SMALL_AMOUNT = 300;
    private static final int FORBIDDEN_AMOUNT = 2100;

    @ParameterizedTest()
    @MethodSource("dataForTestRollback")
    void testRollback(int amount) {
        List<Banknote> banknotes = empty100s();
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

    @Test
    void withdrawForbiddenIfNotEnoughDiffBanknotes() {
        assertFalse(strategy.isWithdrawPossible(SMALL_AMOUNT, empty50s()));
        assertFalse(strategy.isWithdrawPossible(SMALL_AMOUNT, empty100s()));
        assertFalse(strategy.isWithdrawPossible(SMALL_AMOUNT, empty200s()));
    }

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

    @Test
    void withdrawValid() {
        assertTrue(strategy.isWithdrawPossible(SMALL_AMOUNT, fullBanknotes()));
        assertTrue(strategy.isWithdrawPossible(MAX_AMOUNT, fullBanknotes()));
    }

    @Test
    void withdrawForbiddenBigAmount() {
        assertFalse(strategy.isWithdrawPossible(FORBIDDEN_AMOUNT, fullBanknotes()));
        assertEquals(Collections.emptyList(), strategy.withdraw(FORBIDDEN_AMOUNT, fullBanknotes()));
    }

    @Test
    void withdrawForbiddenSmallAmount() {
        assertFalse(strategy.isWithdrawPossible(FORBIDDEN_SMALL_AMOUNT, fullBanknotes()));
        assertEquals(Collections.emptyList(), strategy.withdraw(FORBIDDEN_SMALL_AMOUNT, fullBanknotes()));
    }

    @ParameterizedTest
    @MethodSource("dataForWithdrawTest")
    void withdrawValidCheckBeforeAndAfter(int amount, List<Banknote> availableBanknotes, List<Banknote> leftInATM,
                                          List<BanknoteDTO> withdraw) {
        List<BanknoteDTO> actualWithdraw = strategy.withdraw(amount, availableBanknotes);
        actualWithdraw.sort(Comparator.comparingInt(BanknoteDTO::getDenomination));
        assertEquals(leftInATM, availableBanknotes);
        assertEquals(amount, getAmountFromListDTO(withdraw));
        assertEquals(withdraw, actualWithdraw);
    }

    @Parameterized.Parameters
    private static Collection<Object[]> dataForWithdrawTest() {
        return Arrays.asList(new Object[][]{
                {SMALL_AMOUNT, fullBanknotes(), mixedStrategyRes1(), resDTO4()},
                {MAX_AMOUNT, fullBanknotes(), mixedStrategyRes2(), resDTO5()},
                {MAX_AMOUNT, low200s(), mixedStrategyRes3(), resDTO6()}}
        );
    }
}