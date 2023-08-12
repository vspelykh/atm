package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vspelykh.atm.model.converter.AccountConverter;
import ua.vspelykh.atm.model.converter.TransactionConverter;
import ua.vspelykh.atm.model.dto.TransactionDTO;
import ua.vspelykh.atm.model.entity.Account;
import ua.vspelykh.atm.model.entity.Transaction;
import ua.vspelykh.atm.model.repository.AccountRepository;
import ua.vspelykh.atm.model.repository.TransactionRepository;
import ua.vspelykh.atm.util.exception.NotEnoughMoneyToTransferException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private final TransactionConverter transactionConverter;
    private final AccountConverter accountConverter;

    public List<String> getAccountsOfCurrentUser(Principal principal) {
        String currentAccount = principal.getName();
        Integer userId = accountRepository.findByAccountNumber(currentAccount).getUserId();

        List<Account> accounts = accountRepository.findAllByUserId(userId);
        return accounts.stream().map(Account::getAccountNumber)
                .filter(accountNumber -> !accountNumber.equals(currentAccount))
                .toList();
    }

    @Transactional
    public TransactionDTO performTransaction(Double amount, String accountFrom, String accountTo) throws NotEnoughMoneyToTransferException {
        Account from = accountRepository.findByAccountNumber(accountFrom);
        Account to = accountRepository.findByAccountNumber(accountTo);
        double fee = getTransferFee(amount, from, to);
        Transaction transaction = verifyAndCreateTransaction(amount, fee, from, to);
        transactionRepository.save(transaction);
        return transactionConverter.toDto(transaction);
    }

    private double getTransferFee(Double amount, Account from, Account to) {
        return !from.getUserId().equals(to.getUserId()) ? amount * 0.01 : 0;
    }

    private Transaction verifyAndCreateTransaction(Double amount, double fee, Account from, Account to) throws NotEnoughMoneyToTransferException {
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
        throw new NotEnoughMoneyToTransferException();
    }
}
