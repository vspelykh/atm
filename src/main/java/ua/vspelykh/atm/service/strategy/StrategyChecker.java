package ua.vspelykh.atm.service.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.vspelykh.atm.model.entity.Banknote;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.util.List;

import static ua.vspelykh.atm.util.exception.ExceptionMessages.WITHDRAW_NOT_POSSIBLE;


/**
 * A utility class to check and retrieve the allowed withdrawal strategies for a given withdrawal amount
 * and the available banknotes in the ATM.
 *
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class StrategyChecker {

    private final List<WithdrawStrategy> withdrawalStrategies;

    public List<StrategyType> getAllowedStrategies(int amountToWithdraw, List<Banknote> availableBanknotes) throws ServiceException {
        List<StrategyType> strategyTypes = withdrawalStrategies.stream()
                .filter(strategy -> strategy.isWithdrawPossible(amountToWithdraw, availableBanknotes))
                .map(WithdrawStrategy::getStrategyType)
                .toList();
        if (strategyTypes.isEmpty()) {
            throw new ServiceException(WITHDRAW_NOT_POSSIBLE);
        }
        return strategyTypes;
    }

    public WithdrawStrategy getStrategy(StrategyType strategyType) {
        return withdrawalStrategies.stream()
                .filter(strategy -> strategy.supportsStrategyType(strategyType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown strategy type: " + strategyType));
    }
}