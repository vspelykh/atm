INSERT INTO users (first_name, last_name, phone_number, role)
VALUES ('John', 'Doe', '+1 123-456-7890', 'user'),
       ('Alice', 'Smith', '+1 987-654-3210', 'user'),
       ('Bob', 'Johnson', '+1 555-123-4567', 'user');

INSERT INTO accounts (account_number, balance, user_id, password, issue_date)
VALUES ('1234567890123456', 5000.00, 1, 'pass123', '2023-07-23'),
       ('9876543210987654', 3000.00, 2, 'pass456', '2023-07-23'),
       ('1111222233334444', 10000.00, 3, 'pass789', '2023-07-23');

INSERT INTO transactions (amount, transaction_date, source_account_id, destination_account_id, user_id)
VALUES (1000.00, '2023-07-23 10:30:00', 1, NULL, 1),
       (2000.00, '2023-07-23 11:15:00', NULL, 2, 2),
       (500.00, '2023-07-23 12:00:00', 3, 1, 3);

INSERT INTO atms (location, availability)
VALUES ('New York, 5th Avenue', 'Yes'),
       ('San Francisco, Market St', 'Yes'),
       ('Los Angeles, Hollywood Blvd', 'No');
