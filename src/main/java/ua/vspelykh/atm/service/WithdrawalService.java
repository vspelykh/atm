package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.vspelykh.atm.model.converter.AccountConverter;
import ua.vspelykh.atm.model.converter.WithdrawalConverter;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.repository.AccountRepository;
import ua.vspelykh.atm.model.repository.WithdrawalRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;
    private final AccountRepository accountRepository;
    private final WithdrawalConverter withdrawalConverter;
    private final AccountConverter accountConverter;

    public List<BanknoteDTO> makeWithdraw(BigDecimal amount) {

        return null;
    }

}