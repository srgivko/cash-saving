package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.Category;
import by.sivko.cashsaving.models.Event;
import by.sivko.cashsaving.services.CategoryService;
import by.sivko.cashsaving.services.EventService;
import by.sivko.cashsaving.services.SavingFileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
@SessionAttributes("event")
public class EventController {

    private final CategoryService categoryService;

    private final EventService eventService;

    private final SavingFileService savingFileService;

    public EventController(CategoryService categoryService, EventService eventService, SavingFileService savingFileService) {
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.savingFileService = savingFileService;
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

    @RequestMapping(value = {"/category/*/event/add", "/category/*/event/*/edit"}, method = RequestMethod.POST)
    public String addEvent(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @Valid Event event,
            BindingResult bindingResult,
            HttpSession httpSession,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAllAttributes(errors);
            model.addAttribute("eventTypes", Event.Type.values());
            return "eventForm";
        }
        event.setImgFilename(this.savingFileService.saveFile(file));
        this.eventService.addEvent(event);
        return String.format("redirect:%s", httpSession.getAttribute("backUrl"));
    }

    @RequestMapping(value = "/category/*/event/{eventId}/delete", method = RequestMethod.GET)
    public String removeEventById(@PathVariable Long eventId, HttpServletRequest httpServletRequest) {
        this.eventService.removeEventById(eventId);
        return String.format("redirect:%s", httpServletRequest.getHeader("Referer"));
    }
}
