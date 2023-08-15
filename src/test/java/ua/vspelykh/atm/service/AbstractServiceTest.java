package ua.vspelykh.atm.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.vspelykh.atm.model.mapper.AccountMapper;
import ua.vspelykh.atm.model.mapper.TransactionMapper;
import ua.vspelykh.atm.model.repository.*;
import ua.vspelykh.atm.util.exception.RepositoryException;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.security.Principal;

abstract class AbstractServiceTest {

    @Mock
    protected AccountRepository accountRepository;

    @Mock
    protected TransactionRepository transactionRepository;

    @Mock
    protected WithdrawalRepository withdrawalRepository;

    @Mock
    protected BanknoteRepository banknoteRepository;

    @Mock
    protected ATMRepository atmRepository;

    @Mock
    protected AccountMapper accountMapper;

    @Mock
    protected TransactionMapper transactionMapper;

    @Mock
    protected Principal principal;

    @BeforeEach
    public void setup() throws ServiceException, RepositoryException {
        MockitoAnnotations.openMocks(this);
        prepareMocks();
    }

    protected abstract void prepareMocks() throws ServiceException, RepositoryException;
}
