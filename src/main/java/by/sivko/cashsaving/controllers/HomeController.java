package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.models.Event;
import by.sivko.cashsaving.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/")
    public String showHomePage(Principal principal, Model model) {
        Collection<Category> categories = this.categoryService.getAllCategoriesByUserUsername(principal.getName());
        Collection<Event> eventHistory = categories.stream().flatMap(category -> category.getEventList().stream())
                .sorted(Comparator.comparing(Event::getCreateAt))
                .collect(Collectors.toList());
        model.addAttribute("categories", categories);
        model.addAttribute("eventHistory", eventHistory);
        return "home";
    }

}