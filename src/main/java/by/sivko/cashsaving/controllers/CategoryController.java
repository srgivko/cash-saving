package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.services.CategoryService;
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

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = {"/add", "/{categoryId}/edit"}, method = RequestMethod.GET)
    public String showCategoryPage(Model model, @PathVariable(required = false) Long categoryId) {
        Category category;
        if (categoryId == null) {
            category = new Category();
        } else {
            category = this.categoryService.getCategoryById(categoryId).orElseThrow(RuntimeException::new);
        }
        model.addAttribute("category", category);
        model.addAttribute("eventHistory", category.getEventList());
        return "categoryForm";
    }

    @RequestMapping(value = {"/add", "/*/edit"}, method = RequestMethod.POST)
    public String addCategory(@ModelAttribute Category category, Principal principal) {
        this.categoryService.addCategory(category, principal.getName());
        return "redirect:/";
    }

    @RequestMapping(value = "/{categoryId}/delete", method = RequestMethod.GET)
    public String removeCategory(@PathVariable Long categoryId) {
        this.categoryService.removeCategoryById(categoryId);
        return "redirect:/";
    }

}
