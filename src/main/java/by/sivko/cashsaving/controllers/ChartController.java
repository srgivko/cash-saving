package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.exceptions.NotFoundEntityException;
import by.sivko.cashsaving.models.Event;
import by.sivko.cashsaving.services.CategoryService;
import by.sivko.cashsaving.utils.chart.ChartData;
import by.sivko.cashsaving.utils.chart.ChartDataCreator;
import by.sivko.cashsaving.utils.chart.ChartDataPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/chart")
public class ChartController {

    private final ChartDataCreator chartDataCreator;
    private final CategoryService categoryService;

    @Autowired
    public ChartController(CategoryService categoryService, ChartDataCreator chartDataCreator) {
        this.categoryService = categoryService;
        this.chartDataCreator = chartDataCreator;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ChartData getChart(Principal principal,
                              @RequestParam ChartDataPeriod period, @RequestParam(required = false) Long categoryId) {
        Stream<Event> eventStream = getEventStreamByCategoryId(categoryId, principal.getName());
        return chartDataCreator.createChartData(eventStream, period);
    }

    private Stream<Event> getEventStreamByCategoryId(Long categoryId, String username) {
        Stream<Event> eventStream;
        if (categoryId == null)
            eventStream = this.categoryService.getAllCategoriesByUserUsername(username).stream().flatMap(category -> category.getEventList().stream());
        else
            eventStream = this.categoryService.getCategoryById(categoryId).orElseThrow(NotFoundEntityException::new).getEventList().stream();
        if (eventStream == null) throw new NotFoundEntityException();
        return eventStream;
    }

}
