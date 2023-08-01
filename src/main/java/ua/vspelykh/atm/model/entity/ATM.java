package ua.vspelykh.atm.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.util.Objects;

import static ua.vspelykh.atm.model.util.Columns.AVAILABILITY;
import static ua.vspelykh.atm.model.util.Columns.LOCATION;
import static ua.vspelykh.atm.model.util.Tables.ATMS;

/**
 * Represents an ATM in the ATM system.
 *
 * @version 1.0
 */
@Entity
@Table(name = ATMS)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ATM extends AbstractBaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(name = LOCATION)
    private String location;

    @NotBlank
    @Size(max = 3)
    @Column(name = AVAILABILITY)
    private String availability;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ATM atm = (ATM) o;
        return getId() != null && Objects.equals(getId(), atm.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
