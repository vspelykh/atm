DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS atms;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id           SERIAL PRIMARY KEY,
    first_name   VARCHAR(50)         NOT NULL,
    last_name    VARCHAR(50)         NOT NULL,
    address      VARCHAR(100),
    phone_number VARCHAR(20),
    email        VARCHAR(100) UNIQUE NOT NULL,
    role         VARCHAR(20)         NOT NULL
);

CREATE TABLE accounts
(
    id            SERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL,
    balance        NUMERIC(12, 2) NOT NULL,
    user_id        INTEGER REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    password       VARCHAR(100) NOT NULL,
    issue_date     DATE NOT NULL
);

CREATE INDEX idx_accounts_account_number ON accounts (account_number);

CREATE TABLE transactions
(
    id                 SERIAL PRIMARY KEY,
    transaction_type   VARCHAR(20)    NOT NULL,
    amount             NUMERIC(12, 2) NOT NULL,
    transaction_date   TIMESTAMP      NOT NULL,
    source_account     VARCHAR(20),
    destination_account VARCHAR(20),
    user_id            INTEGER REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE atms
(
    id           SERIAL PRIMARY KEY,
    location     VARCHAR(100) NOT NULL,
    availability VARCHAR(3)   NOT NULL
);
