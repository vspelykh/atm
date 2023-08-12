package ua.vspelykh.atm.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static ua.vspelykh.atm.model.util.Tables.BANKNOTES;

/**
 * Represents an ATM banknotes in the ATM system.
 *
 * @version 1.0
 */
@Entity
@Table(name = BANKNOTES)
@Getter
@Setter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public class Banknote extends AbstractBaseEntity {
    private int atmId;
    private int denomination;
    private int quantity;

    @Override
    public String toString() {
        return denomination + " * " + quantity;
    }
}
