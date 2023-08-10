package ua.vspelykh.atm.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.vspelykh.atm.model.entity.Banknote;

import java.util.List;

@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Integer> {

    List<Banknote> findAllByAtmId(Integer id);
}
