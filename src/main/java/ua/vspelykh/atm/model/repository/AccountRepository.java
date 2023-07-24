package ua.vspelykh.atm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vspelykh.atm.model.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}