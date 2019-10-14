package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.models.dto.CaptchaResponseDto;
import by.sivko.cashsaving.services.CaptchaService;
import by.sivko.cashsaving.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("user")
public class RegistrationController {

    private final UserService userService;

    private final CaptchaService captchaService;

    @Autowired
    public RegistrationController(UserService userService, CaptchaService captchaService) {
        this.userService = userService;
        this.captchaService = captchaService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUser(
            User user,
            @RequestParam(value = "g-recaptcha-response", defaultValue = "default") String captchaResponse
    ) {
        CaptchaResponseDto captchaResponseDto = this.captchaService.checkCaptcha(captchaResponse);
        if (!captchaResponseDto.isSuccess()) {
            return "registration";
        }
        this.userService.addUser(user);
        return "redirect:/login";
    }


    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public String activate(@PathVariable String code) {
        this.userService.activateUser(code);
        return "login";
    }
}
