package ua.vspelykh.atm.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.vspelykh.atm.model.util.Columns;

/**
 * A base entity class providing common properties for other entities.
 * This class is marked as a MappedSuperclass to share common attributes with its subclasses.
 *
 * @version 1.0
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class AbstractBaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = Columns.ID)
    private Integer id;

    /**
     * Checks if the entity is new (i.e., not yet persisted in the database).
     *
     * @return True if the entity is new, otherwise False.
     */
    public boolean isNew() {
        return this.id == null;
    }
}