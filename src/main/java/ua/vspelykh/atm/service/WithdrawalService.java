package ua.vspelykh.atm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.vspelykh.atm.config.AtmIdHolder;
import ua.vspelykh.atm.model.converter.AccountConverter;
import ua.vspelykh.atm.model.converter.WithdrawalConverter;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Banknote;
import ua.vspelykh.atm.model.entity.Withdrawal;
import ua.vspelykh.atm.model.repository.ATMRepository;
import ua.vspelykh.atm.model.repository.AccountRepository;
import ua.vspelykh.atm.model.repository.BanknoteRepository;
import ua.vspelykh.atm.model.repository.WithdrawalRepository;
import ua.vspelykh.atm.service.strategy.StrategyChecker;
import ua.vspelykh.atm.service.strategy.StrategyType;
import ua.vspelykh.atm.service.strategy.WithdrawStrategy;
import ua.vspelykh.atm.util.exception.NoAvailableWithdrawStrategiesException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;
    private final BanknoteRepository banknoteRepository;
    private final AccountRepository accountRepository;
    private final ATMRepository atmRepository;

    private final WithdrawalConverter withdrawalConverter;
    private final AccountConverter accountConverter;

    private final StrategyChecker strategyChecker;

    private final AtmIdHolder atmIdHolder;

    public List<StrategyType> getPossibleStrategies(int amount) {
        return strategyChecker.getAllowedStrategies(amount, banknoteRepository.findAllByAtmId(atmIdHolder.getAtmId()));
    }

    @Transactional
    public List<BanknoteDTO> performWithdrawal(int amount, StrategyType strategyType) throws NoAvailableWithdrawStrategiesException {
        WithdrawStrategy strategy = strategyChecker.getStrategy(strategyType);
        List<Banknote> availableBanknotes = banknoteRepository.findAllByAtmId(atmIdHolder.getAtmId());
        if (strategy.isWithdrawPossible(amount, availableBanknotes)) {
            Withdrawal withdrawal = createWithdrawal(amount);
            withdrawalRepository.save(withdrawal);
            return strategy.withdraw(amount, availableBanknotes);
        } else throw new NoAvailableWithdrawStrategiesException();
    }

    private Withdrawal createWithdrawal(int amount) {
        return Withdrawal.builder().atm(atmRepository.getReferenceById(atmIdHolder.getAtmId()))
                .account(accountRepository.getReferenceById(1))
                .amount(amount)
                .transactionDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

}