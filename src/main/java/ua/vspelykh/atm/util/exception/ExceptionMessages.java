package ua.vspelykh.atm.util.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String WITHDRAW_EXCEPTION = "Withdraw not available anymore!";
    public static final String NOT_ENOUGH_MONEY_TO_TRANSFER = "You haven't enough money to transfer!";
    public static final String NOT_ENOUGH_MONEY_TO_WITHDRAW = "You haven't enough money to withdraw!";
    public static final String ACCOUNT_NOT_FOUND = "Account to transfer not found!";
}
