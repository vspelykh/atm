package ua.vspelykh.atm.controller.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants class containing page names and redirects used within the application.
 *
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageNames {

    public static final String HOME = "index";
    public static final String REDIRECT_HOME = "redirect:index";
    public static final String AMOUNT = "amount";
    public static final String STRATEGIES = "strategies";
    public static final String LOGIN = "login";

    public static final String REDIRECT_TO_WITHDRAWAL_RESULT = "redirect:/withdraw/result";
    public static final String REDIRECT_TO_TRANSFER_RESULT = "redirect:/transfer/result";
    public static final String WITHDRAWAL_RESULT = "withdraw-result";
    public static final String TRANSFER_RESULT = "transfer-result";

    public static final String CLIENT_ACCOUNTS = "my-accounts";
    public static final String TRANSFER =  "transfer";

}
