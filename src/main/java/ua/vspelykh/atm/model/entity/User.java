package ua.vspelykh.atm.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static ua.vspelykh.atm.model.util.Columns.*;
import static ua.vspelykh.atm.model.util.Tables.USERS;

/**
 * Represents a user of the ATM system.
 *
 * @version 1.0
 */
@Entity
@Table(name = USERS)
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends AbstractBaseEntity {

    @NotBlank
    @Size(max = 50)
    @Column(name = FIRST_NAME)
    private String firstName;

    @NotBlank
    @Column(name = LAST_NAME)
    private String lastName;

    @NotBlank
    @Size(max = 20)
    @Column(name = PHONE_NUMBER)
    private String phoneNumber;

    @NotBlank
    @Size(max = 20)
    @Column(name = ROLE)
    private String role;

    @OneToMany(mappedBy = USER)
    private Set<Account> accounts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
