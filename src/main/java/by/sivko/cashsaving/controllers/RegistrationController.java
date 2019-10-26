package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.models.dto.CaptchaResponseDto;
import by.sivko.cashsaving.models.dto.RestorePasswordDto;
import by.sivko.cashsaving.services.CaptchaService;
import by.sivko.cashsaving.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

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
            @RequestParam(value = "g-recaptcha-response", defaultValue = "default") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        CaptchaResponseDto captchaResponseDto = this.captchaService.checkCaptcha(captchaResponse);
        if (!captchaResponseDto.isSuccess()) {
            bindingResult.addError(new FieldError("user", "captcha", "Fill captcha"));
        }

        if (user.getVerifyPassword().isEmpty()) {
            bindingResult.addError(new FieldError("user", "verifyPassword", "Confirmation password cannot be empty"));
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getVerifyPassword())) {
            bindingResult.addError(new FieldError("user", "verifyPassword", "Password are different!"));
        }

        if (this.userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult.addError(new FieldError("user", "username", "User exists with this username!"));
        }

        if (this.userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult.addError(new FieldError("user", "email", "User exists with this email!"));
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        this.userService.addUser(user);
        return "redirect:/login?activationCode";
    }


    @RequestMapping(value = "/activate/{code}", method = RequestMethod.GET)
    public String activate(@PathVariable String code, Model model) {
        try {
            this.userService.activateUser(code);
            model.addAttribute("statusMessageType", "success");
            model.addAttribute("statusMessage", "User successfully activated");
        } catch (NotFoundEntityException ex) {
            model.addAttribute("statusMessageType", "error");
            model.addAttribute("statusMessage", "Activation code is not found");
        }
        return "login";
    }

    @RequestMapping(value = "/login/forget", method = RequestMethod.GET)
    public String restorePasswordRequest(@RequestParam(required = false) String email) {
        if (email != null) {
            try {
                this.userService.setActivateCodeAndSendEmailForRestorePassword(email);
                return "redirect:/login/forget?success";
            } catch (NotFoundEntityException ex) {
                return "redirect:/login/forget?error";
            }
        }
        return "forgetPassword";
    }

    @RequestMapping(value = "/login/forget/{code}", method = RequestMethod.GET)
    public String showNewPasswordPage(@PathVariable String code, Model model) {
        if (!this.userService.isExistUserByActivateCode(code)) {
            return "redirect:/login?error";
        }
        RestorePasswordDto restorePasswordDto = new RestorePasswordDto();
        restorePasswordDto.setCode(code);
        model.addAttribute("restorePasswordDto", restorePasswordDto);
        return "newPassword";
    }

    @RequestMapping(value = "/login/forget", method = RequestMethod.POST)
    public String changePassword(@Valid RestorePasswordDto restorePasswordDto, BindingResult bindingResult, Model model) {

        if (restorePasswordDto.getCode() == null) {
            bindingResult.addError(new FieldError("restorePasswordDto", "code", "Problem with forget activation code"));
        }

        if (restorePasswordDto.getPassword() != null && !restorePasswordDto.getPassword().equals(restorePasswordDto.getPassword2())) {
            bindingResult.addError(new FieldError("restorePasswordDto", "password2", "Password are different!"));
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "newPassword";
        }

        try {
            this.userService.changePassword(restorePasswordDto.getCode(), restorePasswordDto.getPassword());
        } catch (NotFoundEntityException ex) {
            return "redirect:/login?error";
        }
        return "redirect:/login?successRestorePassword";
    }
}
