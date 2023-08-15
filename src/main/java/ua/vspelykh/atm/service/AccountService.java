package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vspelykh.atm.model.entity.Account;
import ua.vspelykh.atm.model.repository.AccountRepository;
import ua.vspelykh.atm.util.exception.ServiceException;

import static ua.vspelykh.atm.util.exception.ExceptionMessages.ACCOUNT_OR_USER_NOT_FOUND;

/**
 * Service class responsible for handling account-related operations.
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * Retrieves the balance of an account based on the provided account number.
     *
     * @param accountNumber The account number for the account to retrieve the balance.
     * @return The balance of the account.
     */
    public Double getBalanceByAccountNumber(String accountNumber) throws ServiceException {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ServiceException(ACCOUNT_OR_USER_NOT_FOUND));
        return account.getBalance();
    }
}

