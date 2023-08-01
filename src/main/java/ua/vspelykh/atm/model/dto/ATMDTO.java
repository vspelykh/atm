package ua.vspelykh.atm.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class ATMDTO extends AbstractDTO {
    private String location;
    private String availability;
}
