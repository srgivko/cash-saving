package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.dto.RegistrationUserDto;
import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        RegistrationUserDto user = new RegistrationUserDto();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registrationJspPage");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("user") @Valid RegistrationUserDto user, BindingResult bindingResult) {

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registrationJspPage");
        } else {
            // Registration successful, save user
            // Set user role to USER and set it as active
            // TODO: 9/25/19 change convert to User
            User user1 = new User();
            user1.setPassword(user.getPassword());
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());

            userService.addUser(user1);

            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("homeJspPage");
        }
        return modelAndView;
    }
}
