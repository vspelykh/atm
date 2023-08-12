package ua.vspelykh.atm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.vspelykh.atm.model.entity.Account;

import java.security.Principal;

import static ua.vspelykh.atm.controller.utils.PageNames.HOME;
import static ua.vspelykh.atm.controller.utils.PageUrls.HOME_URL;

@Controller
public class PageController {

    @GetMapping(HOME_URL)
    public String index() {
        return HOME;
    }
}
