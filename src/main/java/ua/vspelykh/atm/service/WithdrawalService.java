package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vspelykh.atm.config.AtmIdHolder;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Account;
import ua.vspelykh.atm.model.entity.Banknote;
import ua.vspelykh.atm.model.entity.Withdrawal;
import ua.vspelykh.atm.model.repository.ATMRepository;
import ua.vspelykh.atm.model.repository.AccountRepository;
import ua.vspelykh.atm.model.repository.BanknoteRepository;
import ua.vspelykh.atm.model.repository.WithdrawalRepository;
import ua.vspelykh.atm.service.strategy.StrategyChecker;
import ua.vspelykh.atm.service.strategy.StrategyType;
import ua.vspelykh.atm.service.strategy.WithdrawStrategy;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static ua.vspelykh.atm.util.exception.ExceptionMessages.NOT_ENOUGH_MONEY_TO_WITHDRAW;
import static ua.vspelykh.atm.util.exception.ExceptionMessages.WITHDRAW_EXCEPTION;

/**
 * Service class responsible for managing withdrawal operations.
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;
    private final BanknoteRepository banknoteRepository;
    private final AccountRepository accountRepository;
    private final ATMRepository atmRepository;

    private final StrategyChecker strategyChecker;
    private final AtmIdHolder atmIdHolder;

    /**
     * Gets a list of allowed withdrawal strategies for the specified amount.
     *
     * @param amount The amount to withdraw.
     * @return A list of allowed strategy types.
     * @throws ServiceException If the amount on the balance is not enough to withdraw money.
     */
    public List<StrategyType> getPossibleStrategies(int amount, Principal principal) throws ServiceException {
        Account account = accountRepository.findByAccountNumber(principal.getName());
        if (hasEnoughMoney(account, amount)) {
            return strategyChecker.getAllowedStrategies(amount, banknoteRepository.findAllByAtmId(atmIdHolder.getAtmId()));
        }
        throw new ServiceException(NOT_ENOUGH_MONEY_TO_WITHDRAW);
    }

    /**
     * Performs a withdrawal using the specified strategy and deducts the amount from the account.
     *
     * @param amount        The amount to withdraw.
     * @param strategyType  The strategy type to use for the withdrawal.
     * @param accountNumber The account number for the withdrawal.
     * @return A list of BanknoteDTO representing the withdrawn banknotes.
     * @throws ServiceException If no valid withdrawal strategy is available.
     */
    @Transactional
    public List<BanknoteDTO> performWithdrawal(int amount, StrategyType strategyType, String accountNumber) throws ServiceException {
        WithdrawStrategy strategy = strategyChecker.getStrategy(strategyType);
        List<Banknote> availableBanknotes = banknoteRepository.findAllByAtmId(atmIdHolder.getAtmId());
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (strategy.isWithdrawPossible(amount, availableBanknotes) && hasEnoughMoney(account, amount)) {
            Withdrawal withdrawal = createWithdrawal(amount, account);
            withdrawalRepository.save(withdrawal);
            account.setBalance(account.getBalance() - amount);
            return strategy.withdraw(amount, availableBanknotes);
        } else {
            throw new ServiceException(WITHDRAW_EXCEPTION);
        }
    }

    /**
     * Checks if an account has enough balance for a withdrawal.
     *
     * @param account The account to check.
     * @param amount  The withdrawal amount.
     * @return True if the account has enough balance, false otherwise.
     */
    private boolean hasEnoughMoney(Account account, int amount) {
        return accountRepository.existsByAccountNumberAndBalanceGreaterThanEqual(account.getAccountNumber(), (double) amount);
    }

    /**
     * Creates a withdrawal record.
     *
     * @param amount  The withdrawal amount.
     * @param account The account from which the withdrawal is made.
     * @return The created Withdrawal object.
     */
    private Withdrawal createWithdrawal(int amount, Account account) {
        return Withdrawal.builder()
                .atm(atmRepository.getReferenceById(atmIdHolder.getAtmId()))
                .account(account)
                .amount(amount)
                .transactionDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    /**
     * Methdod returns List of available denominations of banknotes in the current ATM.
     *
     * @param atmId id of current ATM.
     * @return List of available denominations of banknotes in the current ATM.
     */
    public List<Integer> getAvailableDenominations(int atmId) {
        List<Banknote> availableBanknotes = banknoteRepository.findAllByAtmIdAndQuantityGreaterThanOrderByDenomination(atmId, 0);
        return availableBanknotes.stream().map(Banknote::getDenomination).toList();
    }
}