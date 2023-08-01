package ua.vspelykh.atm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AbstractDTO implements Serializable {

    public Integer id;
}
