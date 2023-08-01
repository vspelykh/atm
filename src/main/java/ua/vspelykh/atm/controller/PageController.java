package ua.vspelykh.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ua.vspelykh.atm.controller.utils.PageNames.HOME;
import static ua.vspelykh.atm.controller.utils.PageUrls.HOME_URL;

@Controller
public class PageController {

    @GetMapping(HOME_URL)
    public String index() {
        return HOME;
    }
}
