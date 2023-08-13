package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vspelykh.atm.model.dto.AccountDTO;
import ua.vspelykh.atm.model.entity.Account;
import ua.vspelykh.atm.model.mapper.AccountMapper;
import ua.vspelykh.atm.model.repository.AccountRepository;

/**
 * Service class responsible for handling account-related operations.
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    /**
     * Finds and retrieves an account DTO using the provided account number.
     *
     * @param accountNumber The account number for the account to retrieve.
     * @return The corresponding AccountDTO, or null if not found.
     */
    public AccountDTO findByAccountNumber(String accountNumber) {
        return accountMapper.toDTO(accountRepository.findByAccountNumber(accountNumber));
    }

    /**
     * Retrieves the balance of an account based on the provided account number.
     *
     * @param accountNumber The account number for the account to retrieve the balance.
     * @return The balance of the account.
     */
    public Double getBalanceByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        return account.getBalance();
    }
}
