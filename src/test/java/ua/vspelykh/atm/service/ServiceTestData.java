package ua.vspelykh.atm.service;

import ua.vspelykh.atm.config.AtmIdHolder;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.model.entity.Account;
import ua.vspelykh.atm.model.entity.User;
import ua.vspelykh.atm.service.strategy.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

class ServiceTestData {

    static final Integer ID_VALUE = 1;
    static final String ACCOUNT_NUMBER_VALUE = "1234567890123456";
    static final Double BALANCE_VALUE = 5000.00;
    static final LocalDate ISSUE_DATE_VALUE = LocalDate.of(2024, 10, 1);
    static final String PASSWORD_VALUE = "$2a$12$2Wsb3oNJfwe6N5Wz/FuzXOmXq2uowEmVenpEszVbNTNkJaSP1bMg6";

    static final String NAME_VALUE = "Kirk";
    static final String SURNAME_VALUE = "Hammett";
    static final String NUMBER_VALUE = "+2881233";
    static final String ROLE_VALUE = "user";

    static double AMOUNT_SMALL_VALUE = 100.00;

    static AtmIdHolder atmIdHolder() {
        return new AtmIdHolder(ID_VALUE);
    }


    static User getTestUser() {
        return User.builder()
                .id(ID_VALUE)
                .firstName(NAME_VALUE)
                .lastName(SURNAME_VALUE)
                .phoneNumber(NUMBER_VALUE)
                .role(ROLE_VALUE)
                .accounts(Set.of(getTestAccount()))
                .build();
    }

    static Account getTestAccount() {
        return Account.builder().id(ID_VALUE)
                .accountNumber(ACCOUNT_NUMBER_VALUE)
                .balance(BALANCE_VALUE)
                .issueDate(ISSUE_DATE_VALUE)
                .password(PASSWORD_VALUE)
                .build();
    }

    static List<StrategyType> getStrategyTypes() {
        return List.of(StrategyType.SMALL, StrategyType.MIXED, StrategyType.BIG);
    }

    static List<WithdrawStrategy> getAllStrategies() {
        return List.of(new SmallBanknoteWithdrawStrategy(atmIdHolder()),
                new MixedBanknotesWithdrawStrategy(atmIdHolder()), new BigBanknoteWithdrawStrategy(atmIdHolder()));
    }

    static BanknoteDTO getTestBanknoteDTO(){
        return BanknoteDTO.builder()
                .id(ID_VALUE)
                .atmId(ID_VALUE)
                .denomination(100)
                .quantity(1)
                .build();
    }
}
