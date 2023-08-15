package ua.vspelykh.atm.controller.rest;

import ua.vspelykh.atm.model.dto.BanknoteDTO;

public class RestTestData {

    static final String ACCOUNT_NUMBER_VALUE = "1234567890123456";


    static BanknoteDTO getTestBanknoteDTO50() {
        return BanknoteDTO.builder()
                .id(1)
                .atmId(1)
                .denomination(50)
                .quantity(1)
                .build();
    }

    static BanknoteDTO getTestBanknoteDTO20() {
        return BanknoteDTO.builder()
                .id(1)
                .atmId(1)
                .denomination(20)
                .quantity(2)
                .build();
    }

    static BanknoteDTO getTestBanknoteDTO10() {
        return BanknoteDTO.builder()
                .id(1)
                .atmId(1)
                .denomination(10)
                .quantity(1)
                .build();
    }

}
