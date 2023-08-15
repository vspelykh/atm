package ua.vspelykh.atm.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Withdrawal;
import ua.vspelykh.atm.service.strategy.SmallBanknoteWithdrawStrategy;
import ua.vspelykh.atm.service.strategy.StrategyChecker;
import ua.vspelykh.atm.service.strategy.StrategyType;
import ua.vspelykh.atm.util.exception.RepositoryException;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static ua.vspelykh.atm.service.ServiceTestData.*;
import static ua.vspelykh.atm.service.strategy.StrategyTestData.fullBanknotes;

@RunWith(MockitoJUnitRunner.class)
class WithdrawalServiceTest extends AbstractServiceTest {
    @Mock
    private StrategyChecker strategyChecker;

    private WithdrawalService withdrawalService;

    @Mock
    private SmallBanknoteWithdrawStrategy strategy;

    @BeforeEach
    public void setup() throws ServiceException, RepositoryException {
        super.setup();
        withdrawalService = new WithdrawalService(withdrawalRepository, banknoteRepository,
                accountRepository, atmRepository, strategyChecker, atmIdHolder());
    }

    @Test
    void getPossibleStrategiesSuccess() throws ServiceException {
        List<StrategyType> actual = withdrawalService.getPossibleStrategies((int) AMOUNT_SMALL_VALUE, principal);
        assertEquals(getStrategyTypes(), actual);
    }

    @Test
    void getPossibleStrategiesException() {
        when(accountRepository.existsByAccountNumberAndBalanceGreaterThanEqual(ACCOUNT_NUMBER_VALUE, AMOUNT_SMALL_VALUE))
                .thenReturn(false);
        assertThrows(ServiceException.class, () -> withdrawalService.getPossibleStrategies((int) AMOUNT_SMALL_VALUE, principal));
    }

    @Test
    void performWithdrawalSuccess() throws ServiceException {
        when(strategy.isWithdrawPossible(anyInt(), any())).thenReturn(true);
        when(strategy.withdraw(anyInt(), any())).thenReturn(List.of(getTestBanknoteDTO()));
        List<BanknoteDTO> actual =
                withdrawalService.performWithdrawal((int) AMOUNT_SMALL_VALUE, StrategyType.SMALL, ACCOUNT_NUMBER_VALUE);
        int sum = actual.stream().mapToInt(b -> b.getDenomination() * b.getQuantity()).sum();
        assertEquals(AMOUNT_SMALL_VALUE, sum);
        verify(withdrawalRepository, times(1)).save(any(Withdrawal.class));
    }

    @Test
    void performWithdrawalNotEnoughMoney() {
        when(accountRepository.existsByAccountNumberAndBalanceGreaterThanEqual(ACCOUNT_NUMBER_VALUE, AMOUNT_SMALL_VALUE))
                .thenReturn(false);
        assertThrows(ServiceException.class, () ->
                withdrawalService.performWithdrawal((int) AMOUNT_SMALL_VALUE, StrategyType.SMALL, ACCOUNT_NUMBER_VALUE));
        verify(withdrawalRepository, never()).save(any(Withdrawal.class));
    }

    @Test
    void performWithdrawalNotAvailable() {
        when(strategy.isWithdrawPossible(anyInt(), any())).thenReturn(false);
        assertThrows(ServiceException.class, () ->
                withdrawalService.performWithdrawal((int) AMOUNT_SMALL_VALUE, StrategyType.SMALL, ACCOUNT_NUMBER_VALUE));
        verify(withdrawalRepository, never()).save(any(Withdrawal.class));
    }

    @Test
    void performWithdrawalNullableParams() {
        assertThrows(ServiceException.class,
                () -> withdrawalService.performWithdrawal((int) AMOUNT_SMALL_VALUE, null, null));
        verify(withdrawalRepository, never()).save(any(Withdrawal.class));

    }

    @Override
    protected void prepareMocks() throws ServiceException {
        when(principal.getName()).thenReturn(ACCOUNT_NUMBER_VALUE);
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER_VALUE)).thenReturn(Optional.of(getTestAccount()));
        when(banknoteRepository.findAllByAtmId(ID_VALUE)).thenReturn(fullBanknotes());
        when(strategyChecker.getAllowedStrategies((int) AMOUNT_SMALL_VALUE, fullBanknotes())).thenReturn(getStrategyTypes());
        when(strategyChecker.getStrategy(any())).thenReturn(strategy);
        when(accountRepository.existsByAccountNumberAndBalanceGreaterThanEqual(ACCOUNT_NUMBER_VALUE, AMOUNT_SMALL_VALUE))
                .thenReturn(true);
    }
}