package ua.vspelykh.atm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.vspelykh.atm.config.AtmIdHolder;
import ua.vspelykh.atm.controller.utils.AttributeNames;
import ua.vspelykh.atm.service.WithdrawalService;
import ua.vspelykh.atm.service.strategy.StrategyType;

import static ua.vspelykh.atm.controller.utils.AttributeNames.DENOMINATIONS;
import static ua.vspelykh.atm.controller.utils.AttributeNames.TYPE;
import static ua.vspelykh.atm.controller.utils.PageNames.*;
import static ua.vspelykh.atm.controller.utils.PageUrls.*;

/**
 * Controller class responsible for handling withdrawal-related page requests.
 *
 * @version 1.0
 */
@Controller
@RequestMapping(WITHDRAW_URL)
@RequiredArgsConstructor
public class WithdrawPagesController {

    private final WithdrawalService withdrawalService;
    private final AtmIdHolder atmIdHolder;

    /**
     * Handles a GET request to the amount page.
     *
     * @return The name of the amount view to display.
     */
    @GetMapping(AMOUNT_URL)
    public String amount(Model model) {
        model.addAttribute(DENOMINATIONS, withdrawalService.getAvailableDenominations(atmIdHolder.getAtmId()));
        return AMOUNT;
    }

    /**
     * Handles a POST request to choose a withdrawal strategy.
     *
     * @param amount The withdrawal amount.
     * @param model  The Model object to add attributes.
     * @return The name of the strategies view to display.
     */
    @PostMapping(STRATEGIES_URL)
    public String chooseStrategy(@RequestParam Integer amount, Model model) {
        model.addAttribute(AttributeNames.AMOUNT, amount);
        return STRATEGIES;
    }

    /**
     * Handles a POST request to process a withdrawal.
     *
     * @param amount             The withdrawal amount.
     * @param type               The withdrawal strategy type.
     * @param redirectAttributes RedirectAttributes object to add flash attributes.
     * @return A redirect to the withdrawal result page.
     */
    @PostMapping(PROCESS_URL)
    public String withdrawProcess(@ModelAttribute(AttributeNames.AMOUNT) Integer amount,
                                  @ModelAttribute(TYPE) StrategyType type,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(AttributeNames.AMOUNT, amount);
        redirectAttributes.addFlashAttribute(TYPE, type);
        return REDIRECT_TO_WITHDRAWAL_RESULT;
    }

    /**
     * Handles a GET request to show the withdrawal result page.
     *
     * @param amount The withdrawal amount.
     * @param type   The withdrawal strategy type.
     * @param model  The Model object to add attributes.
     * @return The name of the withdrawal result view or a redirect to the home page if data is missing.
     */
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