package ua.vspelykh.atm.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static ua.vspelykh.atm.model.util.Columns.*;
import static ua.vspelykh.atm.model.util.Tables.TRANSACTIONS;

/**
 * Represents a transaction in the ATM system.
 *
 * @version 1.0
 */
@Entity
@Table(name = TRANSACTIONS)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends AbstractBaseEntity {

    @NotNull
    @Column(name = AMOUNT)
    private BigDecimal amount;

    @NotNull
    @Column(name = TRANSACTION_DATE)
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = SOURCE_ACCOUNT_ID)
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = DESTINATION_ACCOUNT_ID)
    private Account destinationAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction that = (Transaction) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}