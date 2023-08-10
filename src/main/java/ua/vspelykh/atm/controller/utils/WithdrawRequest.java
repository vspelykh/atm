package ua.vspelykh.atm.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.vspelykh.atm.service.strategy.StrategyType;

@Getter
@Setter
@AllArgsConstructor
public class WithdrawRequest {
    private Integer amount;
    private StrategyType type;
}