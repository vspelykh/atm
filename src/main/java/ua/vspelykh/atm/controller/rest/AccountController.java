package ua.vspelykh.atm.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.vspelykh.atm.service.AccountService;

import java.security.Principal;

import static ua.vspelykh.atm.controller.utils.AttributeNames.JSON;
import static ua.vspelykh.atm.controller.utils.PageUrls.API;
import static ua.vspelykh.atm.controller.utils.PageUrls.BALANCE_URL;

/**
 * Controller class for managing account-related operations.
 *
 * @version 1.0
 */
@RestController
@RequestMapping(value = API, produces = JSON)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Retrieves the account balance for the authenticated user.
     *
     * @param principal The authenticated user's principal object.
     * @return ResponseEntity containing the account balance.
     */
    @GetMapping(BALANCE_URL)
    public ResponseEntity<Double> getBalance(Principal principal) {
        return ResponseEntity.ok(accountService.getBalanceByAccountNumber(principal.getName()));
    }
}
