package by.sivko.cashsaving.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class RegistrationController {

   /* private final UserService userService;

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
    public ModelAndView createNewUser(@ModelAttribute("user") @Valid RegistrationUserDto userDto, BindingResult bindingResult) {

        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registrationJspPage");
        } else {
            User user = ConvertorUtil.createUserFromRegistrationUserDto(userDto);
            userService.addUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registrationJspPage");
        }
        return modelAndView;
    }*/
}
