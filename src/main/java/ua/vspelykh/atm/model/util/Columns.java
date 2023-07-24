package ua.vspelykh.atm.model.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This class contains static fields with the names of database columns.
 * These fields can be used to specify column names in JPA annotations.
 *
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Columns {

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String ROLE = "role";
    public static final String ACCOUNT_NUMBER = "account_number";
    public static final String BALANCE = "balance";
    public static final String USER = "user";
    public static final String USER_ID = "user_id";
    public static final String PASSWORD = "password";
    public static final String ISSUE_DATE = "issue_date";
    public static final String AMOUNT = "amount";
    public static final String TRANSACTION_DATE = "transaction_date";
    public static final String ACCOUNT_ID = "account_id";
    public static final String SOURCE_ACCOUNT_ID = "source_account_id";
    public static final String DESTINATION_ACCOUNT_ID = "destination_account_id";
    public static final String ATM_ID = "atm_id";
    public static final String LOCATION = "location";
    public static final String AVAILABILITY = "availability";

    public static final String TIMESTAMP_DEFAULT = "TIMESTAMP DEFAULT NOW()";

}
