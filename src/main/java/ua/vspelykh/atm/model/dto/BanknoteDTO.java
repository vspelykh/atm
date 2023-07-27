package ua.vspelykh.atm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanknoteDTO implements Serializable {
    private Integer id;
    private Integer atmId;
    private Integer denomination;
    private int quantity;
}
