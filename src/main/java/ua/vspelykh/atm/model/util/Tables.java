package ua.vspelykh.atm.model.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class contains static fields with the names of database tables.
 * These fields can be used to specify table names in JPA annotations.
 *
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Tables {

    public static final String USERS = "users";
    public static final String ACCOUNTS = "accounts";
    public static final String TRANSACTIONS = "transactions";
    public static final String WITHDRAWALS = "withdrawals";
    public static final String ATMS = "atms";

}
