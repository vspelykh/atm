package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vspelykh.atm.model.repository.AccountRepository;

@Service
@RequiredArgsConstructor
@Transactional()
public class WithdrawalService {

    private final WithdrawalService withdrawalService;
    private final AccountRepository accountRepository;

}