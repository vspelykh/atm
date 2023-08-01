package ua.vspelykh.atm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.vspelykh.atm.model.entity.ATM;
import ua.vspelykh.atm.model.repository.ATMRepository;

import java.util.List;

@RestController
public class RestTestController {

    public final ATMRepository repository;

    public RestTestController(ATMRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/api/atms", produces = "application/json")
    public List<ATM> atms() {


        return repository.findAll();
    }
}
