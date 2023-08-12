package ua.vspelykh.atm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ua.vspelykh.atm.controller.utils.PageNames.LOGIN;
import static ua.vspelykh.atm.controller.utils.PageUrls.LOGIN_URL;

/**
 * Controller class responsible for handling authentication-related requests.
 *
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    /**
     * Handles a GET request to the login page.
     *
     * @return The name of the login view to display.
     */
    @GetMapping(LOGIN_URL)
    public String login() {
        return LOGIN;
    }
}