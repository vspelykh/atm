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

    // Class Users
    public static final String ID = "id";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ROLE = "role";
    public static final String ID_OF_USER = "user.id";

    // Class Accounts
    public static final String ACCOUNTS_ID = "id";
    public static final String ACCOUNT = "account";
    public static final String ACCOUNT_NUMBER = "accountNumber";
    public static final String BALANCE = "balance";
    public static final String USER_ID = "userId";
    public static final String PASSWORD = "password";
    public static final String ISSUE_DATE = "issueDate";

    // Class Transactions
    public static final String TRANSACTIONS_ID = "id";
    public static final String AMOUNT = "amount";
    public static final String TRANSACTION_DATE = "transactionDate";
    public static final String SOURCE_ACCOUNT_ID = "sourceAccountId";
    public static final String SOURCE_ACCOUNT = "sourceAccount";
    public static final String DESTINATION_ACCOUNT = "destinationAccount";
    public static final String DESTINATION_ACCOUNT_ID = "destinationAccountId";

    // Class Atms
    public static final String ATMS_ID = "id";
    public static final String LOCATION = "location";
    public static final String AVAILABILITY = "availability";

    // Class AtmBanknotes
    public static final String ATM_BANKNOTES_ID = "id";
    public static final String ATM_ID = "atmId";
    public static final String ATM = "atm";
    public static final String DENOMINATION = "denomination";
    public static final String QUANTITY = "quantity";

    // Class Withdrawals
    public static final String WITHDRAWALS_ID = "id";
    public static final String WITHDRAWAL_AMOUNT = "amount";
    public static final String WITHDRAWAL_TRANSACTION_DATE = "transactionDate";
    public static final String ACCOUNT_ID = "accountId";

    public static final String ENTITY_ID = "entity.id";
    public static final String ENTITY_FIRST_NAME = "entity.firstName";
    public static final String ENTITY_LAST_NAME = "entity.lastName";
    public static final String ENTITY_PHONE_NUMBER = "entity.phoneNumber";
    public static final String ENTITY_ROLE = "entity.role";
    public static final String DTO_ID = "dto.id";
    public static final String DTO_FIRST_NAME = "dto.firstName";
    public static final String DTO_LAST_NAME = "dto.lastName";
    public static final String DTO_PHONE_NUMBER = "dto.phoneNumber";
    public static final String DTO_ROLE = "dto.role";

    public static final String USER = "user";

    public static final String TIMESTAMP_DEFAULT = "TIMESTAMP DEFAULT NOW()";

}
