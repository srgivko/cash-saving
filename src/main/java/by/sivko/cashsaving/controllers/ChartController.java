package by.sivko.cashsaving.controllers;

import by.sivko.cashsaving.models.Event;
import by.sivko.cashsaving.services.CategoryService;
import by.sivko.cashsaving.services.EventService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/chart")
public class ChartController {
    private final CategoryService categoryService;

    @Autowired
    public ChartController(CategoryService categoryService, EventService eventService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ChartDto getChart(Principal principal, @RequestParam Period period, @RequestParam(required = false) Long categoryId) {
        Stream<Event> eventStream;
        if (categoryId == null)
            eventStream = this.categoryService.getAllCategoriesByUserUsername(principal.getName()).stream().flatMap(category -> category.getEventList().stream());
        else
            eventStream = this.categoryService.getCategoryById(categoryId).orElseThrow(RuntimeException::new).getEventList().stream();
        if (eventStream == null) return null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ChartDto chartDto = new ChartDto();
        eventStream.sorted(Comparator.comparing(Event::getCreateAt))
                .forEachOrdered(event -> {
                    calculateIncomeAndOutCome(format, event, chartDto);
                });
        calculateProfit(chartDto);
        calculateTotalIncomeAndTotalOutcome(chartDto);
        if (period != null)
            calulateByPeriod(chartDto, period);
        return chartDto;
    }

    private void calculateTotalIncomeAndTotalOutcome(ChartDto chartDto) {
        chartDto.setTotalIncome(chartDto.getDataIncome().stream().reduce(BigDecimal::add).orElseThrow(RuntimeException::new));
        chartDto.setTotalOutcome(chartDto.getDataOutcome().stream().reduce(BigDecimal::add).orElseThrow(RuntimeException::new));
    }

    private void calculateIncomeAndOutCome(DateFormat format, Event event, ChartDto chartDto) {
        String stringDate = format.format(event.getCreateAt());
        int countValues = chartDto.getLabels().size();
        if (chartDto.getPrevDate().equals(stringDate)) {
            if (event.getType() == Event.Type.INCOME) {
                chartDto.getDataIncome().set(countValues - 1, chartDto.getDataIncome().get(countValues - 1).add(event.getAmount()));
            } else {
                chartDto.getDataOutcome().set(countValues - 1, chartDto.getDataOutcome().get(countValues - 1).add(event.getAmount()));
            }
            chartDto.setPrevDate(stringDate);
        } else {
            chartDto.getLabels().add(stringDate);
            chartDto.setPrevDate(stringDate);
            if (event.getType() == Event.Type.INCOME) {
                chartDto.getDataIncome().add(event.getAmount());
                chartDto.getDataOutcome().add(BigDecimal.ZERO);
            } else {
                chartDto.getDataIncome().add(BigDecimal.ZERO);
                chartDto.getDataOutcome().add(event.getAmount());
            }
        }
    }

    private void calculateProfit(ChartDto chartDto) {
        for (int i = 0; i < chartDto.getLabels().size(); i++) {
            chartDto.getDataProfit().add(chartDto.getDataIncome().get(i).subtract(chartDto.getDataOutcome().get(i)));
        }
    }

    private void calulateByPeriod(ChartDto chartDto, Period period) {
        int countCutSymbol = 0;
        switch (period) {
            case DAY:
                return;
            case MONTH:
                countCutSymbol = 8;
                break;
            case YEAR:
                countCutSymbol = 5;
        }
        String remainderMonth = "";
        for (int i = 0; i < chartDto.getLabels().size(); i++) {
            String yearAndMonth = chartDto.getLabels().get(i).substring(0, countCutSymbol);
            if (!yearAndMonth.equals(remainderMonth)) {
                remainderMonth = yearAndMonth;
            } else {
                chartDto.getDataIncome().set(i, chartDto.getDataIncome().get(i).add(chartDto.getDataIncome().get(i - 1)));
                chartDto.getDataOutcome().set(i, chartDto.getDataOutcome().get(i).add(chartDto.getDataOutcome().get(i - 1)));
                chartDto.getDataProfit().set(i, chartDto.getDataProfit().get(i).add(chartDto.getDataProfit().get(i - 1)));
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class ChartDto {
        @JsonIgnore
        private String prevDate = "";
        private List<String> labels = new ArrayList<>();
        private List<BigDecimal> dataIncome = new ArrayList<>();
        private List<BigDecimal> dataOutcome = new ArrayList<>();
        private List<BigDecimal> dataProfit = new ArrayList<>();
        private BigDecimal totalIncome = BigDecimal.ZERO;
        private BigDecimal totalOutcome = BigDecimal.ZERO;
    }

    private enum Period {
        DAY, MONTH, YEAR
    }
}
