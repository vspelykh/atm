package ua.vspelykh.atm.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vspelykh.atm.controller.utils.TransferRequest;
import ua.vspelykh.atm.model.dto.TransactionDTO;
import ua.vspelykh.atm.service.TransactionService;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.security.Principal;
import java.util.List;

import static ua.vspelykh.atm.controller.utils.AttributeNames.JSON;
import static ua.vspelykh.atm.controller.utils.PageUrls.*;

/**
 * Controller class responsible for handling transaction-related operations.
 *
 * @version 1.0
 */
@RestController
@RequestMapping(value = API, produces = JSON)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Retrieves the accounts associated with the current user.
     *
     * @param principal The principal object representing the authenticated user.
     * @return ResponseEntity containing a list of account identifiers.
     */
    @GetMapping(ACCOUNTS_URL)
    public ResponseEntity<List<String>> getAccountsOfCurrentUser(Principal principal) {
        List<String> accountsOfCurrentUser = transactionService.getAccountsOfCurrentUser(principal);
        return ResponseEntity.ok(accountsOfCurrentUser);
    }

    /**
     * Performs a money transfer transaction between accounts.
     *
     * @param principal The principal object representing the authenticated user.
     * @param request   The transfer request containing transfer details.
     * @return ResponseEntity containing transaction details or a BAD_REQUEST response in case of insufficient funds.
     */
    @PostMapping(TRANSFER_URL)
    public ResponseEntity<TransactionDTO> performTransfer(Principal principal,
                                                          @RequestBody TransferRequest request) throws ServiceException {
        TransactionDTO transaction =
                transactionService.performTransaction(request.getAmount(), principal.getName(), request.getAccountTo());
        return ResponseEntity.ok(transaction);
    }
}
