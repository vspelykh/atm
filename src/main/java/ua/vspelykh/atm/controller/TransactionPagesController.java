package ua.vspelykh.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.vspelykh.atm.controller.utils.AttributeNames;

import static ua.vspelykh.atm.controller.utils.AttributeNames.ACCOUNT_TO;
import static ua.vspelykh.atm.controller.utils.PageNames.*;
import static ua.vspelykh.atm.controller.utils.PageUrls.*;

@Controller
@RequestMapping(TRANSFER_URL)
public class TransactionPagesController {

    @GetMapping(ACCOUNTS_URL)
    public String chooseCardsOfCurrentUser() {

        return CLIENT_ACCOUNTS;
    }

    @GetMapping(SEND_URL)
    public String sendToAnotherPersonAccount(){

        return TRANSFER;
    }

    @PostMapping(PROCESS_URL)
    public String withdrawProcess(@ModelAttribute(ACCOUNT_TO) String accountTo,
                                  @ModelAttribute(AttributeNames.AMOUNT) Integer amount,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(ACCOUNT_TO, accountTo);
        redirectAttributes.addFlashAttribute(AttributeNames.AMOUNT, amount);
        return REDIRECT_TO_TRANSFER_RESULT;
    }

    @GetMapping(RESULT_URL)
    public String showSuccessPage(@ModelAttribute(ACCOUNT_TO) String accountTo,
                                  @ModelAttribute(AttributeNames.AMOUNT) Integer amount,
                                  Model model) {
        if (amount == null || accountTo == null) {
            return REDIRECT_HOME;
        }

        model.addAttribute(AttributeNames.AMOUNT, amount);
        model.addAttribute(ACCOUNT_TO, accountTo);

        return TRANSFER_RESULT;
    }
}
