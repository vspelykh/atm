package ua.vspelykh.atm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.List;

/**
 * Repository interface for managing Banknote entities.
 *
 * @version 1.0
 */
@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Integer> {

    /**
     * Retrieves a list of banknotes available in a specific ATM.
     *
     * @param atmId The ID of the ATM.
     * @return A list of banknotes available in the specified ATM.
     */
    List<Banknote> findAllByAtmId(Integer atmId);

    /**
     * Retrieves a list of banknotes available in a specific ATM and quantity greater than needed.
     *
     * @param atmId The ID of the ATM.
     * @return A list of banknotes available in the specified ATM.
     */
    List<Banknote> findAllByAtmIdAndQuantityGreaterThanOrderByDenomination(Integer atmId, Integer minQuantity);
}