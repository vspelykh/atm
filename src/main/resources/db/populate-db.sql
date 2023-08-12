INSERT INTO users (first_name, last_name, phone_number, role)
VALUES ('John', 'Doe', '+1 123-456-7890', 'user'),
       ('Alice', 'Smith', '+1 987-654-3210', 'user'),
       ('Bob', 'Johnson', '+1 555-123-4567', 'user');

INSERT INTO accounts (account_number, balance, user_id, password, issue_date)
VALUES ('1234567890123456', 5000.00, 1, '$2a$12$2Wsb3oNJfwe6N5Wz/FuzXOmXq2uowEmVenpEszVbNTNkJaSP1bMg6', '2024-07-23'),
       ('4321567890123456', 5000.00, 1, '$2a$12$2Wsb3oNJfwe6N5Wz/FuzXOmXq2uowEmVenpEszVbNTNkJaSP1bMg6', '2024-07-23'),
       ('9876543210987654', 3000.00, 2, '$2a$12$2Wsb3oNJfwe6N5Wz/FuzXOmXq2uowEmVenpEszVbNTNkJaSP1bMg6', '2024-07-23'),
       ('1111222233334444', 10000.00, 3, '$2a$12$2Wsb3oNJfwe6N5Wz/FuzXOmXq2uowEmVenpEszVbNTNkJaSP1bMg6', '2024-07-23');

INSERT INTO authorities (account_number, authority)
values ('1234567890123456', 'ROLE_CLIENT'),
       ('9876543210987654', 'ROLE_CLIENT');

INSERT INTO transactions (amount, transaction_date, source_account_id, destination_account_id)
VALUES (1000.00, '2023-07-23 10:30:00', 1, 2),
       (2000.00, '2023-07-23 11:15:00', 1, 3),
       (500.00, '2023-07-23 12:00:00', 3, 1);

INSERT INTO atms (location, availability)
VALUES ('New York, 5th Avenue', 'Yes'),
       ('San Francisco, Market St', 'Yes'),
       ('Los Angeles, Hollywood Blvd', 'No');

INSERT INTO atm_banknotes (atm_id, denomination, quantity)
VALUES (1, 10, 100),
       (1, 20, 100),
       (1, 50, 50),
       (1, 100, 150),
       (1, 200, 200),
       (1, 500, 50);
