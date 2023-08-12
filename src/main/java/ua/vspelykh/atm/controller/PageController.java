package ua.vspelykh.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ua.vspelykh.atm.controller.utils.PageNames.HOME;
import static ua.vspelykh.atm.controller.utils.PageUrls.HOME_URL;

/**
 * Controller class responsible for handling page-related requests.
 *
 * @version 1.0
 */
@Controller
public class PageController {

    /**
     * Handles a GET request to the home page.
     *
     * @return The name of the home view to display.
     */
    @GetMapping(HOME_URL)
    public String index() {
        return HOME;
    }
}