package ua.vspelykh.atm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.service.WithdrawalService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WithdrawalController {

    public final WithdrawalService withdrawalService;

    @PostMapping(value = "/api/withdraw", produces = "application/json")
    public List<BanknoteDTO> makeWithdraw(@RequestParam("amount") BigDecimal amount) {
        return withdrawalService.makeWithdraw(amount);
    }
}
