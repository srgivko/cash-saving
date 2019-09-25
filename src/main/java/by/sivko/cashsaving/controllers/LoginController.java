package by.sivko.cashsaving.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("homeJspPage");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        if (principal != null) modelAndView.setViewName("homeJspPage");
        else modelAndView.setViewName("loginJspPage");
        return modelAndView;
    }
}
