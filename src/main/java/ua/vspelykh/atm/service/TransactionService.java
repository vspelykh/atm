package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vspelykh.atm.model.dto.TransactionDTO;
import ua.vspelykh.atm.model.entity.Account;
import ua.vspelykh.atm.model.entity.Transaction;
import ua.vspelykh.atm.model.mapper.TransactionMapper;
import ua.vspelykh.atm.model.repository.AccountRepository;
import ua.vspelykh.atm.model.repository.TransactionRepository;
import ua.vspelykh.atm.util.exception.RepositoryException;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static ua.vspelykh.atm.util.exception.ExceptionMessages.*;

/**
 * Service class responsible for handling transaction-related operations.
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private final TransactionMapper transactionMapper;

    /**
     * Retrieves a list of account numbers associated with the current user.
     *
     * @param principal The principal object representing the authenticated user.
     * @return A list of account numbers.
     * @throws ServiceException If there are no user or account of user.
     */
    public List<String> getAccountsOfCurrentUser(Principal principal) throws ServiceException {
        String currentAccount = principal.getName();
        try {
            Integer userId = getAccount(currentAccount).getUser().getId();
            List<Account> accounts = accountRepository.findAllByUserId(userId);
            return accounts.stream().map(Account::getAccountNumber)
                    .filter(accountNumber -> !accountNumber.equals(currentAccount))
                    .toList();
        } catch (RepositoryException e) {
            throw new ServiceException(ACCOUNT_OR_USER_NOT_FOUND);
        }
    }

    /**
     * Performs a money transfer transaction between accounts.
     *
     * @param amount      The amount to transfer.
     * @param accountFrom The account to transfer from.
     * @param accountTo   The account to transfer to.
     * @return The DTO representing the performed transaction.
     * @throws ServiceException If there are no account to transfer or
     *                          If there are insufficient funds for the transfer.
     */
    @Transactional
    public TransactionDTO performTransaction(Double amount, String accountFrom, String accountTo) throws ServiceException {
        Account from = getAccount(accountFrom);
        Account to = getAccount(accountTo);
        double fee = getTransferFee(amount, from, to);
        Transaction transaction = verifyAndCreateTransaction(amount, fee, from, to);
        transactionRepository.save(transaction);
        return transactionMapper.toDTO(transaction);
    }

    private Account getAccount(String accountNumber) throws ServiceException {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ServiceException(ACCOUNT_NOT_FOUND));
    }

    /**
     * Calculates the transfer fee based on the transaction amount and account ownership.
     *
     * @param amount The amount being transferred.
     * @param from   The source account.
     * @param to     The destination account.
     * @return The calculated transfer fee.
     */
    private double getTransferFee(Double amount, Account from, Account to) {
        return !from.getUser().getId().equals(to.getUser().getId()) ? amount * 0.01 : 0;
    }

    /**
     * Verifies the transaction and creates a transaction object if valid.
     *
     * @param amount The amount being transferred.
     * @param fee    The calculated transfer fee.
     * @param from   The source account.
     * @param to     The destination account.
     * @return The created transaction object.
     * @throws ServiceException If there are insufficient funds for the transfer.
     */
    private Transaction verifyAndCreateTransaction(Double amount, double fee, Account from, Account to) throws
            ServiceException {
        double updatedFromBalance = from.getBalance() - amount;
        double updatedToBalance = to.getBalance() + amount - fee;
        if (updatedFromBalance >= 0) {
            from.setBalance(updatedFromBalance);
            to.setBalance(updatedToBalance);
            return Transaction.builder()
                    .amount(amount)
                    .sourceAccount(from)
                    .destinationAccount(to)
                    .transactionDate(LocalDateTime.now())
                    .build();
        }
        throw new ServiceException(NOT_ENOUGH_MONEY_TO_TRANSFER);
    }
}