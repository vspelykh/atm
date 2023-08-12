package ua.vspelykh.atm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.vspelykh.atm.model.converter.AccountConverter;
import ua.vspelykh.atm.model.dto.AccountDTO;
import ua.vspelykh.atm.service.AccountService;

import java.security.Principal;

import static ua.vspelykh.atm.controller.utils.PageUrls.HOME_URL;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;
    private final AccountConverter accountConverter;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccess() {

        return HOME_URL;
    }

//    @GetMapping("/logout")
//    public String logout(Principal principal) {
//
//    }
}
