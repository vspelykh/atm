DROP TABLE IF EXISTS withdrawals;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS atms;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id           SERIAL PRIMARY KEY,
    first_name   VARCHAR(50)        NOT NULL,
    last_name    VARCHAR(50)        NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    role         VARCHAR(20)        NOT NULL
);

CREATE TABLE accounts
(
    id             SERIAL PRIMARY KEY,
    account_number VARCHAR(20)    NOT NULL,
    balance        NUMERIC(12, 2) NOT NULL,
    user_id        INTEGER REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    password       VARCHAR(100)   NOT NULL,
    issue_date     DATE           NOT NULL
);

CREATE UNIQUE INDEX idx_accounts_account_number ON accounts (account_number);
CREATE INDEX idx_accounts_user_id ON accounts (user_id);

CREATE TABLE transactions
(
    id                     SERIAL PRIMARY KEY,
    amount                 NUMERIC(12, 2) NOT NULL,
    transaction_date       TIMESTAMP      NOT NULL,
    source_account_id      INTEGER REFERENCES accounts (id),
    destination_account_id INTEGER REFERENCES accounts (id),
    user_id                INTEGER REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_transactions_user_id ON transactions (user_id);

CREATE TABLE atms
(
    id           SERIAL PRIMARY KEY,
    location     VARCHAR(100) NOT NULL,
    availability VARCHAR(3)   NOT NULL
);

CREATE TABLE withdrawals
(
    id               SERIAL PRIMARY KEY,
    amount           NUMERIC(12, 2)          NOT NULL,
    transaction_date TIMESTAMP DEFAULT NOW() NOT NULL,
    account_id       INTEGER REFERENCES accounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
    atm_id           INTEGER REFERENCES atms (id),
    user_id          INTEGER REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE INDEX idx_withdrawals_account_id ON withdrawals (account_id);