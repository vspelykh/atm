package ua.vspelykh.atm.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString
@SuperBuilder
public class BanknoteDTO extends AbstractDTO {
    private Integer atmId;
    private Integer denomination;
    private int quantity;

    @Override
    public String toString() {
        return denomination + " * " + quantity;
    }
}
