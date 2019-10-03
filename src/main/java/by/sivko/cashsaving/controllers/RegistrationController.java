package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.AuthorityType;
import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.repositories.AuthorityRepository;
import by.sivko.cashsaving.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class RegistrationController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.addAuthority(this.authorityRepository.findByType(AuthorityType.ROLE_USER));
        this.userRepository.save(user);
        return "redirect:/login";
    }
}
