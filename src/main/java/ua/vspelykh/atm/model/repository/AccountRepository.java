package ua.vspelykh.atm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vspelykh.atm.model.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAllByUserId(Integer userId);

    Account findByAccountNumber(String accountNumber);

    boolean existsByAccountNumberAndBalanceGreaterThanEqual(String accountId, Integer amount);
}