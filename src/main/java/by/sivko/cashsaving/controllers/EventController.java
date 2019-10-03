package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.models.Event;
import by.sivko.cashsaving.services.CategoryService;
import by.sivko.cashsaving.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("event")
public class EventController {

    private final CategoryService categoryService;

    private final EventService eventService;

    public EventController(CategoryService categoryService, EventService eventService) {
        this.categoryService = categoryService;
        this.eventService = eventService;
    }

    @RequestMapping(value = {"/category/{categoryId}/event/add", "/category/*/event/{eventId}/edit"}, method = RequestMethod.GET)
    public String showEventPage(@PathVariable(required = false) Long categoryId, @PathVariable(required = false) Long eventId, Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        Event event;
        if (eventId == null) {
            Category category = this.categoryService.getCategoryById(categoryId).orElseThrow(RuntimeException::new);
            event = new Event();
            category.addEvent(event);
        } else {
            event = this.eventService.getEventById(eventId).orElseThrow(RuntimeException::new);
        }
        httpSession.setAttribute("backUrl", httpServletRequest.getHeader("Referer"));
        model.addAttribute("event", event);
        model.addAttribute("eventTypes", Event.Type.values());
        return "eventForm";
    }

    @RequestMapping(value = "/category/*/event/add", method = RequestMethod.POST)
    public String addEvent(Event event, HttpSession httpSession) {
        this.eventService.addEvent(event);
        return String.format("redirect:%s", httpSession.getAttribute("backUrl"));
    }

    @RequestMapping(value = "/category/*/event/*/edit", method = RequestMethod.POST)
    public String editEvent(Event event, HttpSession httpSession) {
        this.eventService.addEvent(event);
        return String.format("redirect:%s", httpSession.getAttribute("backUrl"));
    }

    @RequestMapping(value = "/category/*/event/{eventId}/delete", method = RequestMethod.GET)
    public String removeEventById(@PathVariable Long eventId, HttpServletRequest httpServletRequest) {
        Event event = this.eventService.getEventById(eventId).orElseThrow(RuntimeException::new);
        event.getCategory().removeEvent(event);
        this.eventService.removeEvent(event);
        return String.format("redirect:%s", httpServletRequest.getHeader("Referer"));
    }
}
