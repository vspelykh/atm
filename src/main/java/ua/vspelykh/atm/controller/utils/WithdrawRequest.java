package ua.vspelykh.atm.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.vspelykh.atm.service.strategy.StrategyType;

/**
 * Data class representing a withdrawal request.
 *
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class WithdrawRequest {
    private Integer amount;
    private StrategyType type;
}