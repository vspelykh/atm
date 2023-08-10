package ua.vspelykh.atm.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.sql.Timestamp;
import java.util.Objects;

import static ua.vspelykh.atm.model.util.Columns.*;
import static ua.vspelykh.atm.model.util.Tables.WITHDRAWALS;

/**
 * Represents a withdrawal transaction in the ATM system.
 *
 * @version 1.0
 */
@Entity
@Table(name = WITHDRAWALS)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Withdrawal extends AbstractBaseEntity {

    @NotNull
    @Column(name = AMOUNT)
    private Integer amount;

    @NotNull
    @Column(name = TRANSACTION_DATE)
    private Timestamp transactionDate;

    @ManyToOne
    @JoinColumn(name = ACCOUNT_ID)
    private Account account;

    @ManyToOne
    @JoinColumn(name = ATM_ID)
    private ATM atm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Withdrawal withdrawal = (Withdrawal) o;
        return getId() != null && Objects.equals(getId(), withdrawal.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
