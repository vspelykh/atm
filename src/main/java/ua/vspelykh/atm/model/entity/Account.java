package ua.vspelykh.atm.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

import static ua.vspelykh.atm.model.util.Columns.*;
import static ua.vspelykh.atm.model.util.Tables.ACCOUNTS;

/**
 * Represents an account in the ATM system.
 *
 * @version 1.0
 */
@Entity
@Table(name = ACCOUNTS)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public final class Account extends AbstractBaseEntity {

    @NotBlank
    @Size(max = 20)
    @Column(name = ACCOUNT_NUMBER)
    private String accountNumber;

    @NotNull
    @Column(name = BALANCE)
    private Double balance;

    @NotBlank
    @Column(name = PASSWORD)
    private String password;

    @NotNull
    @Column(name = ISSUE_DATE)
    private LocalDate issueDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = USER_ID)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return getId() != null && Objects.equals(getId(), account.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}