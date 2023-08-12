package ua.vspelykh.atm.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vspelykh.atm.controller.utils.TransferRequest;
import ua.vspelykh.atm.controller.utils.WithdrawRequest;
import ua.vspelykh.atm.model.dto.TransactionDTO;
import ua.vspelykh.atm.service.TransactionService;
import ua.vspelykh.atm.util.exception.NotEnoughMoneyToTransferException;

import java.security.Principal;
import java.util.List;

import static ua.vspelykh.atm.controller.utils.AttributeNames.JSON;
import static ua.vspelykh.atm.controller.utils.PageUrls.*;

@RestController
@RequestMapping(value = API, produces = JSON)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(ACCOUNTS_URL)
    public ResponseEntity<List<String>> getAccountsOfCurrentUser(Principal principal) {
        List<String> accountsOfCurrentUser = transactionService.getAccountsOfCurrentUser(principal);
        return ResponseEntity.ok(accountsOfCurrentUser);
    }

    @PostMapping(TRANSFER_URL)
    public ResponseEntity<TransactionDTO> performTransfer(Principal principal, @RequestBody TransferRequest request) {
        try {
            TransactionDTO transaction =
                    transactionService.performTransaction(request.getAmount(), principal.getName(), request.getAccountTo());
            return ResponseEntity.ok(transaction);
        } catch (NotEnoughMoneyToTransferException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
