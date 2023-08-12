package ua.vspelykh.atm.controller.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransferRequest {
    private Double amount;
    private String accountTo;
}
