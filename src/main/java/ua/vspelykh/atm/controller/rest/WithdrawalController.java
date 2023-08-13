package ua.vspelykh.atm.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vspelykh.atm.controller.utils.WithdrawRequest;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.service.WithdrawalService;
import ua.vspelykh.atm.service.strategy.StrategyType;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.security.Principal;
import java.util.List;

import static ua.vspelykh.atm.controller.utils.AttributeNames.JSON;
import static ua.vspelykh.atm.controller.utils.PageUrls.*;

/**
 * Controller class responsible for handling withdrawal-related API requests.
 *
 * @version 1.0
 */
@RestController
@RequestMapping(value = API, produces = JSON)
@RequiredArgsConstructor
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    /**
     * Handles a GET request to retrieve possible withdrawal strategies based on the requested amount.
     *
     * @param amount The withdrawal amount requested.
     * @return A list of StrategyType enumerations representing possible strategies.
     */
    @GetMapping(value = STRATEGIES_URL)
    public List<StrategyType> getPossibleStrategies(@RequestParam Integer amount,
                                                    Principal principal) throws ServiceException {
        return withdrawalService.getPossibleStrategies(amount, principal);
    }

    /**
     * Handles a POST request to perform a withdrawal operation.
     *
     * @param request   The WithdrawRequest object containing withdrawal details.
     * @param principal The Principal object representing the authenticated user.
     * @return A ResponseEntity containing a list of BanknoteDTO objects if successful,
     * or a ResponseEntity with a BAD_REQUEST status if no strategies are available.
     */
    @PostMapping(WITHDRAW_URL)
    public ResponseEntity<List<BanknoteDTO>> makeWithdraw(@RequestBody WithdrawRequest request,
                                                          Principal principal) throws ServiceException {
        String accountNumber = principal.getName();
        List<BanknoteDTO> banknotes =
                withdrawalService.performWithdrawal(request.getAmount(), request.getType(), accountNumber);
        return ResponseEntity.ok(banknotes);
    }
}
