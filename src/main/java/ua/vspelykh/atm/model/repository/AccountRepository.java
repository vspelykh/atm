package ua.vspelykh.atm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vspelykh.atm.model.entity.Account;
import ua.vspelykh.atm.util.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Account entities.
 *
 * @version 1.0
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Retrieves a list of accounts associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of accounts associated with the user.
     */
    List<Account> findAllByUserId(Integer userId) throws RepositoryException;

    /**
     * Retrieves an account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return The account with the specified account number, or null if not found.
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Checks if an account with a specified account number has a balance greater than or equal to a given amount.
     *
     * @param accountNumber The account number to check.
     * @param amount        The amount to compare the balance against.
     * @return True if the account with the specified account number has a balance greater than or equal to the amount, false otherwise.
     */
    boolean existsByAccountNumberAndBalanceGreaterThanEqual(String accountNumber, Double amount);
}