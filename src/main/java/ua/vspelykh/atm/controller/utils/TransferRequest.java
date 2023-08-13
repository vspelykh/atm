package ua.vspelykh.atm.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data class representing a Transaction request.
 *
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class TransferRequest {
    private Double amount;
    private String accountTo;
}
