package ua.vspelykh.atm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.vspelykh.atm.controller.utils.WithdrawRequest;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.service.WithdrawalService;
import ua.vspelykh.atm.service.strategy.StrategyType;
import ua.vspelykh.atm.util.exception.NoAvailableWithdrawStrategiesException;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
@RequiredArgsConstructor
public class WithdrawalController {

    public final WithdrawalService withdrawalService;

    @GetMapping(value = "/strategies")
    public List<StrategyType> getPossibleStrategies(@RequestParam Integer amount) {
        return withdrawalService.getPossibleStrategies(amount);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<List<BanknoteDTO>> makeWithdraw(@RequestBody WithdrawRequest request) {
        try {
            List<BanknoteDTO> banknotes = withdrawalService.performWithdrawal(request.getAmount(), request.getType());
            return ResponseEntity.ok(banknotes);
        } catch (NoAvailableWithdrawStrategiesException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}