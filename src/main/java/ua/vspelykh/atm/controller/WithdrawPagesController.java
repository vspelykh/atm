package ua.vspelykh.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.vspelykh.atm.controller.utils.AttributeNames;
import ua.vspelykh.atm.service.strategy.StrategyType;

import static ua.vspelykh.atm.controller.utils.AttributeNames.TYPE;
import static ua.vspelykh.atm.controller.utils.PageNames.*;
import static ua.vspelykh.atm.controller.utils.PageUrls.*;

@Controller
@RequestMapping(WITHDRAW_URL)
public class WithdrawPagesController {

    @GetMapping(AMOUNT_URL)
    public String index() {
        return AMOUNT;
    }

    @PostMapping(STRATEGIES_URL)
    public String chooseStrategy(@RequestParam Integer amount, Model model) {
        model.addAttribute(AttributeNames.AMOUNT, amount);
        return STRATEGIES;
    }

    @PostMapping(PROCESS_URL)
    public String withdrawProcess(@ModelAttribute(AttributeNames.AMOUNT) Integer amount,
                                  @ModelAttribute(TYPE) StrategyType type,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(AttributeNames.AMOUNT, amount);
        redirectAttributes.addFlashAttribute(TYPE, type);
        return REDIRECT_TO_WITHDRAWAL_RESULT;
    }

    @GetMapping(RESULT_URL)
    public String showSuccessPage(@ModelAttribute(AttributeNames.AMOUNT) Integer amount,
                                  @ModelAttribute(TYPE) StrategyType type,
                                  Model model) {
        if (amount == null || type == null) {
            return REDIRECT_HOME;
        }

        model.addAttribute(AttributeNames.AMOUNT, amount);
        model.addAttribute(TYPE, type);

        return WITHDRAWAL_RESULT;
    }
}
