package by.sivko.cashsaving.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    /*private final CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView homePage(@AuthenticationPrincipal PdfUserDetails user) {
        ModelAndView modelAndView = new ModelAndView();
        List<Category> categoryList = this.categoryService.getAllCategoriesByUserId(user.getId());
        List<Event> allEvents = categoryList.stream().flatMap(category -> category.getEventList().stream()).sorted(Comparator.comparing(Event::getCreateAt)).collect(Collectors.toList());
        modelAndView.addObject("categories", categoryList);
        modelAndView.addObject("eventHistory", allEvents);
        modelAndView.setViewName("homeJspPage");
        return modelAndView;
    }*/
}
