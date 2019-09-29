package by.sivko.cashsaving.controllers;

public class EventController {

   /* private final CategoryService categoryService;

    private final EventService eventService;

    @Autowired
    public EventController(CategoryService categoryService, EventService eventService) {
        this.categoryService = categoryService;
        this.eventService = eventService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView showCreateEventPage(@AuthenticationPrincipal PdfUserDetails pdfUserDetails) {
        EventDto eventDto = new EventDto();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("types", Event.Type.values());
        modelAndView.addObject("categories", this.categoryService.getAllCategoriesByUserId(pdfUserDetails.getId()));
        modelAndView.addObject("event", eventDto);
        modelAndView.setViewName("createOrUpdateEventJspPage");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createEventPage(@ModelAttribute("event") @Valid EventDto eventDto, BindingResult bindingResult) {
        ModelAndView modelAndView;
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView();
            modelAndView.setViewName("createOrUpdateEventJspPage");
        } else {
            Category category = this.categoryService.getCategoryById(eventDto.getCategory());
            Event event = ConvertorUtil.createEventFromEventDto(new Event(), eventDto);
            category.addEvent(event);
            this.eventService.addEvent(event);
            modelAndView = new ModelAndView("redirect:/home");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView showEditEventPage(@AuthenticationPrincipal PdfUserDetails pdfUserDetails, @PathVariable long id) {
        Event event = this.eventService.getEventById(id);
        ModelAndView modelAndView = new ModelAndView();
        List<Category> categories = this.categoryService.getAllCategoriesByUserId(pdfUserDetails.getId());
        EventDto eventDto = ConvertorUtil.createEventDtoFromEvent(event);
        modelAndView.addObject("types", Event.Type.values());
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("event", eventDto);
        modelAndView.setViewName("createOrUpdateEventJspPage");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editEvent(@ModelAttribute("event") EventDto eventDto) {
        Event event = ConvertorUtil.createEventFromEventDto(this.eventService.getEventById(eventDto.getId()), eventDto);
        Category newEventCategory = this.categoryService.getCategoryById(eventDto.getCategory());
        newEventCategory.addEvent(event);
        this.eventService.updateEvent(event);
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public ModelAndView removeEvent(@PathVariable long id) {
        this.eventService.removeEventById(id);
        return new ModelAndView("redirect:/home");
    }*/

}
