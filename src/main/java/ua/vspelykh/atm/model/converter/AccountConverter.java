package ua.vspelykh.atm.model.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.vspelykh.atm.model.dto.AccountDTO;
import ua.vspelykh.atm.model.entity.Account;

@Component
@RequiredArgsConstructor
public class AccountConverter {

    private final UserConverter userConverter;

    public AccountDTO toDto(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .userId(account.getUserId())
                .password(account.getPassword())
                .issueDate(account.getIssueDate())
                .user(userConverter.toDto(account.getUser()))
                .build();
    }

    public Account toEntity(AccountDTO accountDTO) {
        return Account.builder()
                .accountNumber(accountDTO.getAccountNumber())
                .balance(accountDTO.getBalance())
                .userId(accountDTO.getUserId())
                .password(accountDTO.getPassword())
                .issueDate(accountDTO.getIssueDate())
                .user(userConverter.toEntity(accountDTO.getUser()))
                .build();
    }
}

