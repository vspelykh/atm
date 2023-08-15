package ua.vspelykh.atm.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import ua.vspelykh.atm.model.entity.Transaction;
import ua.vspelykh.atm.util.exception.RepositoryException;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.vspelykh.atm.service.ServiceTestData.*;

class TransactionServiceTest extends AbstractServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    public void setup() throws ServiceException, RepositoryException {
        super.setup();
        transactionService = new TransactionService(transactionRepository, accountRepository, transactionMapper);
    }

    @Test
    void getAccountsOfCurrentUserSuccess() throws ServiceException, RepositoryException {
        when(accountRepository.findAllByUserId(ID_VALUE)).thenReturn(List.of(getTestAccount2()));
        List<String> accounts = transactionService.getAccountsOfCurrentUser(principal);
        assertEquals(List.of(getTestAccount2().getAccountNumber()), accounts);
    }

    @Test
    void getAccountsOfCurrentUserCurrentAccountNotFound() throws RepositoryException {
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER_VALUE)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> transactionService.getAccountsOfCurrentUser(principal));
    }

    @Test
    void performTransactionSuccess() throws ServiceException {
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER_VALUE)).thenReturn(Optional.of(getTestAccount()));
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER_VALUE_2)).thenReturn(Optional.of(getTestAccount()));
        transactionService.performTransaction(AMOUNT_SMALL_VALUE, ACCOUNT_NUMBER_VALUE, ACCOUNT_NUMBER_VALUE_2);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void performTransactionFailed() {
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER_VALUE)).thenReturn(Optional.of(getTestAccount()));
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER_VALUE_2)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class,
                () -> transactionService.performTransaction(AMOUNT_SMALL_VALUE, ACCOUNT_NUMBER_VALUE, ACCOUNT_NUMBER_VALUE_2));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Override
    protected void prepareMocks() throws RepositoryException {
        when(principal.getName()).thenReturn(ACCOUNT_NUMBER_VALUE);
        when(accountRepository.findByAccountNumber(ACCOUNT_NUMBER_VALUE)).thenReturn(Optional.of(getTestAccount()));
        when(accountRepository.findAllByUserId(ID_VALUE)).thenReturn(List.of(getTestAccount2()));
        when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(getTestTransaction());
    }
}