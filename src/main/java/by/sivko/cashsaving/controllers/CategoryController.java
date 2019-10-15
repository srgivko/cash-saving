package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.services.CategoryService;
import by.sivko.cashsaving.services.SavingFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/category")
@SessionAttributes("category")
public class CategoryController {

    private final CategoryService categoryService;

    private final SavingFileService savingFileService;

    @Autowired
    public CategoryController(CategoryService categoryService, SavingFileService savingFileService) {
        this.categoryService = categoryService;
        this.savingFileService = savingFileService;
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

    @PostMapping({"/add", "/*/edit"})
    public String addCategory(
            @RequestParam(value = "file") MultipartFile file,
            @Valid Category category,
            Principal principal
    ) throws IOException {
        category.setImgFilename(this.savingFileService.saveFile(file));
        this.categoryService.addCategory(category, principal.getName());
        return "redirect:/";
    }

    @RequestMapping(value = "/{categoryId}/delete", method = RequestMethod.GET)
    public String removeCategory(@PathVariable Long categoryId) {
        this.categoryService.removeCategoryById(categoryId);
        return "redirect:/";
    }
}
