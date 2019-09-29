package by.sivko.cashsaving.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

/*    private final CategoryService categoryService;

    private final UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createCategory(@ModelAttribute("category") @Valid CategoryDto categoryDto, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView;
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView();
            modelAndView.setViewName("createCategoryJspPage");
        } else {
            User user = this.userService.findByUsername(principal.getName()).orElseThrow(NotFoundEntityException::new);
            Category category = ConvertorUtil.createCategoryFromCategoryDto(new Category(), categoryDto);
            user.addCategory(category);
            this.categoryService.addCategory(category);
            modelAndView = new ModelAndView("redirect:/home");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView showCreateCategoryPage() {
        ModelAndView modelAndView = new ModelAndView();
        CategoryDto categoryDto = new CategoryDto();
        modelAndView.addObject("category", categoryDto);
        modelAndView.setViewName("createOrUpdateCategoryJspPage");
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditCategoryJspPage(@PathVariable long id) {
        Category category = this.categoryService.getCategoryById(id);
        CategoryDto categoryDto = ConvertorUtil.createCategoryDtoFromCategory(category);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("category", categoryDto);
        modelAndView.addObject("eventHistory", category.getEventList());
        modelAndView.setViewName("createOrUpdateCategoryJspPage");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editCategory(@ModelAttribute("category") @Valid CategoryDto categoryDto) {
        Category category = ConvertorUtil.createCategoryFromCategoryDto(this.categoryService.getCategoryById(categoryDto.getId()), categoryDto);
        this.categoryService.updateCategory(category);
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/remove/{id}")
    public ModelAndView removeCategory(@PathVariable long id) {
        this.categoryService.removeCategoryById(id);
        return new ModelAndView("redirect:/home");
    }*/
}
