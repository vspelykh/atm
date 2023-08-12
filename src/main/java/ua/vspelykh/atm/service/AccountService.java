package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.vspelykh.atm.model.converter.AccountConverter;
import ua.vspelykh.atm.model.dto.AccountDTO;
import ua.vspelykh.atm.model.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    public AccountDTO findByAccountNumber(String accountNumber) {
        return accountConverter.toDto(accountRepository.findByAccountNumber(accountNumber));
    }

}
