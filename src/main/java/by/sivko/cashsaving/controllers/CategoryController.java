package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.models.User;
import by.sivko.cashsaving.services.CategoryService;
import by.sivko.cashsaving.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/category")
@SessionAttributes("category")
public class CategoryController {

    private final CategoryService categoryService;

    private final UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/add", "/{categoryId}/edit"}, method = RequestMethod.GET)
    public String showCategoryPage(Principal principal, Model model, @PathVariable(required = false) Long categoryId) {
        Category category;
        if (categoryId == null) {
            User user = this.userService.findByUsername(principal.getName()).orElseThrow(RuntimeException::new);
            category = new Category();
            user.addCategory(category);
        } else {
            category = this.categoryService.getCategoryById(categoryId).orElseThrow(RuntimeException::new);
        }
        model.addAttribute("category", category);
        model.addAttribute("eventHistory", category.getEventList());
        return "categoryForm";
    }

    @RequestMapping(value = {"/add", "/{categoryId}/edit"}, method = RequestMethod.POST)
    public String addCategory(@ModelAttribute Category category, @PathVariable(required = false) String categoryId) {
        this.categoryService.addCategory(category);
        return "redirect:/";
    }

    @RequestMapping(value = "/{categoryId}/delete", method = RequestMethod.GET)
    public String removeCategory(@PathVariable Long categoryId) {
        Category category = this.categoryService.getCategoryById(categoryId).orElseThrow(RuntimeException::new);
        category.getUser().removeCategory(category);
        this.categoryService.removeCategory(category);
        return "redirect:/";
    }

}
