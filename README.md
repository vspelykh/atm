## ATM Simulator

### Objective

The objective of the ATM Simulator project is to develop a simple and interactive virtual ATM system. The project aims to provide **practical experience** in building a fully functional ATM application using the *Spring Framework* and *Hibernate*, while incorporating essential security features to ensure **ACID transactions**. The primary focus of the project is to practice *Spring*, *Hibernate*, and algorithm development for **currency dispensing** during cash withdrawals.

### Technologies Used

- **Spring Framework**: The project leverages the Spring Framework to implement the core functionalities of the ATM simulator, including *Dependency Injection*, *Aspect-Oriented Programming*, and *Transaction Management*.

- **Hibernate**: Hibernate is used as the Object-Relational Mapping (ORM) tool to facilitate seamless database operations and interactions.

- **PostgreSQL**: The project utilizes PostgreSQL as the relational database management system to store and manage user, account, transaction, and ATM data.

### Features

1. **User Authentication**: Users can log in using their ATM card number and PIN (password).

2. **Account Management**: Each user can have multiple accounts associated with their profile. The system allows users to view account balances, deposit funds, transfer money between accounts, and withdraw cash.

3. **Currency Dispensing Algorithm**: The application incorporates an efficient algorithm for dispensing currency during cash withdrawals, considering various denominations of the local currency (*UAH - Ukrainian Hryvnia*).

4. **Transaction History**: Users can view their transaction history, providing a detailed record of their financial activities.

5. **ATM Availability**: The system keeps track of ATM availability and location information, ensuring that users can only use available ATMs.

6. **Security**: The project emphasizes security by implementing **ACID (Atomicity, Consistency, Isolation, Durability)** properties for all transactions, protecting user data, and securing sensitive operations.

### How to Run

1. Clone the repository and open the project in your preferred IDE.

2. Ensure that PostgreSQL is installed and running, and create a new database for the project.

3. Update the database configuration in the `application.properties` file to match your PostgreSQL database settings.

4. Run the application, and the server will be up and running.

5. Access the ATM simulator via the provided endpoints (REST API) or through a user-friendly interface.

**Note**: This project is developed as a learning exercise to gain hands-on experience with Spring, Hibernate, and algorithm development for currency dispensing. It does not involve real financial transactions or sensitive user information.

### Acknowledgments

Special thanks to the Spring Framework and Hibernate communities for providing powerful tools to build robust and secure applications. This project is inspired by the desire to improve software development skills and understanding of key concepts in financial technology applications.