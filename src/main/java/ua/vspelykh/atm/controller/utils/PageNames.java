package ua.vspelykh.atm.controller.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageNames {

    public static final String HOME = "index";
    public static final String REDIRECT_HOME = "redirect:index";
    public static final String AMOUNT = "amount";
    public static final String STRATEGIES = "strategies";

    public static final String REDIRECT_TO_WITHDRAWAL_RESULT = "redirect:/withdraw/result";
    public static final String WITHDRAWAL_RESULT = "withdraw-result";
}
