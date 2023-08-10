package ua.vspelykh.atm.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.service.strategy.StrategyType;

import java.util.List;

import static ua.vspelykh.atm.controller.utils.PageNames.AMOUNT;
import static ua.vspelykh.atm.controller.utils.PageNames.STRATEGIES;

@Controller
@RequestMapping("/withdraw")
public class WithdrawPagesController {

    @GetMapping("/amount")
    public String index() {
        return AMOUNT;
    }

    @PostMapping("/strategies")
    public String chooseStrategy(@RequestParam Integer amount, Model model) {
        model.addAttribute("amount", amount);
        return STRATEGIES;
    }

    @PostMapping("/process")
    public String withdrawProcess(@ModelAttribute("amount") Integer amount,
                                  @ModelAttribute("type") StrategyType type,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("amount", amount);
        redirectAttributes.addFlashAttribute("type", type);
        return "redirect:/withdraw/result"; // Перенаправлення на GET-метод
    }

    @GetMapping("/result")
    public String showSuccessPage(@ModelAttribute("amount") Integer amount,
                                  @ModelAttribute("type") StrategyType type,
                                  Model model) {
        if (amount == null || type == null) {
            // Обробка ситуації, коли дані не були передані через POST-запит
            return "redirect:/"; // Повернення на початкову сторінку або іншу за вашим вибором
        }

        model.addAttribute("amount", amount);
        model.addAttribute("type", type);

        return "withdraw-result"; // Показ сторінки з результатами
    }

}
