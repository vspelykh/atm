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

/**
 * Controller class responsible for handling transaction-related web pages and processes.
 *
 * @version 1.0
 */
@Controller
@RequestMapping(TRANSFER_URL)
public class TransactionPagesController {

    /**
     * Renders the page for choosing accounts of the current user.
     *
     * @return The view name for displaying the accounts.
     */
    @GetMapping(ACCOUNTS_URL)
    public String chooseCardsOfCurrentUser() {
        return CLIENT_ACCOUNTS;
    }

    /**
     * Renders the page for sending money to another person's account.
     *
     * @return The view name for the transfer page.
     */
    @GetMapping(SEND_URL)
    public String sendToAnotherPersonAccount() {
        return TRANSFER;
    }

    /**
     * Processes the withdrawal of funds and redirects to the transfer result page.
     *
     * @param accountTo          The recipient's account identifier.
     * @param amount             The amount to withdraw.
     * @param redirectAttributes Redirect attributes for passing data between requests.
     * @return Redirect URL to the transfer result page.
     */
    @PostMapping(PROCESS_URL)
    public String withdrawProcess(@ModelAttribute(ACCOUNT_TO) String accountTo,
                                  @ModelAttribute(AttributeNames.AMOUNT) Integer amount,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(ACCOUNT_TO, accountTo);
        redirectAttributes.addFlashAttribute(AttributeNames.AMOUNT, amount);
        return REDIRECT_TO_TRANSFER_RESULT;
    }

    /**
     * Renders the success page after a successful transfer.
     *
     * @param accountTo The recipient's account identifier.
     * @param amount    The transferred amount.
     * @param model     The model for passing data to the view.
     * @return The view name for the transfer result page or a redirect to the home page if data is missing.
     */
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
