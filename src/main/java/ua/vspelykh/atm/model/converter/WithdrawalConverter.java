package ua.vspelykh.atm.model.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.vspelykh.atm.model.dto.WithdrawalDTO;
import ua.vspelykh.atm.model.entity.Withdrawal;

@Component
@RequiredArgsConstructor
public class WithdrawalConverter {

    private final AccountConverter accountConverter;
    private final ATMConverter atmConverter;

    public WithdrawalDTO toDto(Withdrawal withdrawal) {
        return WithdrawalDTO.builder()
                .id(withdrawal.getId())
                .amount(withdrawal.getAmount())
                .transactionDate(withdrawal.getTransactionDate())
                .account(accountConverter.toDto(withdrawal.getAccount()))
                .atm(atmConverter.toDto(withdrawal.getAtm()))
                .build();
    }

    public Withdrawal toEntity(WithdrawalDTO withdrawalDTO) {
        return Withdrawal.builder()
                .amount(withdrawalDTO.getAmount())
                .transactionDate(withdrawalDTO.getTransactionDate())
                .account(accountConverter.toEntity(withdrawalDTO.getAccount()))
                .atm(atmConverter.toEntity(withdrawalDTO.getAtm()))
                .build();
    }
}