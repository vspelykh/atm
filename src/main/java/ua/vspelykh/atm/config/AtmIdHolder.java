package ua.vspelykh.atm.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A simple data class representing an ATM's identifier.
 *
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class AtmIdHolder {
    private int atmId;
}
