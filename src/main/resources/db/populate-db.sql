INSERT INTO users (first_name, last_name, address, phone_number, email, role)
VALUES ('John', 'Doe', '123 Main St, New York', '+1 123-456-7890', 'john.doe@example.com', 'user'),
       ('Alice', 'Smith', '456 Park Ave, San Francisco', '+1 987-654-3210', 'alice.smith@example.com', 'user'),
       ('Bob', 'Johnson', '789 Broadway, Los Angeles', '+1 555-123-4567', 'bob.johnson@example.com', 'user');

INSERT INTO accounts (account_number, balance, user_id, password, issue_date)
VALUES ('1234567890123456', 5000.00,  1, 'pass123', '2023-07-23'),
       ('9876543210987654', 3000.00, 2, 'pass456', '2023-07-23'),
       ('1111222233334444', 10000.00, 3, 'pass789', '2023-07-23');

INSERT INTO transactions (transaction_type, amount, transaction_date, source_account, destination_account, user_id)
VALUES ('debit', 1000.00, '2023-07-23 10:30:00', '1234567890123456', NULL, 1),
       ('credit', 2000.00, '2023-07-23 11:15:00', NULL, '9876543210987654', 2),
       ('transfer', 500.00, '2023-07-23 12:00:00', '1111222233334444', '1234567890123456', 3);

INSERT INTO atms (location, availability)
VALUES ('New York, 5th Avenue', 'Yes'),
       ('San Francisco, Market St', 'Yes'),
       ('Los Angeles, Hollywood Blvd', 'No');
