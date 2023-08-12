package ua.vspelykh.atm.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static ua.vspelykh.atm.controller.utils.PageUrls.*;

/**
 * Constants class that holds various configuration-related values.
 *
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigConstants {

    static final String[] PERMIT_ALL_URLS = {HOME_URL, "/js/**", "/css/**", "/img/**"};
    static final String[] AUTHENTICATED_URLS = {ALL_WITHDRAW_URLS, ALL_API, LOGOUT_URL};
    static final String[] ANONYMOUS_URLS = {LOGIN_URL};

    static final String SQL_LOG_ACCOUNT = "SELECT account_number, password, 1 as enabled " +
            "FROM accounts " +
            "WHERE account_number = ?";
    static final String SQL_LOG_AUTHORITIES = "SELECT account_number, authority " +
            "FROM authorities " +
            "WHERE account_number = ?";
}
