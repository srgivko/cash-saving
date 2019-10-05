package by.sivko.cashsaving.utils.chart.chartjs;

import by.sivko.cashsaving.models.Event;
import by.sivko.cashsaving.utils.chart.ChartData;
import by.sivko.cashsaving.utils.chart.ChartDataCreator;
import by.sivko.cashsaving.utils.chart.ChartDataPeriod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ChartJSChartDataCreatorImpl implements ChartDataCreator {

    @Override
    public ChartJSData createChartData(Stream<Event> eventStream, ChartDataPeriod chartDataPeriod) {
        return fillData(eventStream, chartDataPeriod);
    }

    private ChartJSData fillData(Stream<Event> eventStream, ChartDataPeriod chartDataPeriod) {
        ChartJSData chartJSData = new ChartJSData();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        eventStream.sorted(Comparator.comparing(Event::getCreateAt))
                .forEachOrdered(event -> fillIncomeAndOutcome(format, event, chartJSData));
        return chartJSData.calculateData(chartDataPeriod);
    }

    private void fillIncomeAndOutcome(DateFormat format, Event event, ChartJSData chartJSData) {
        String stringDate = format.format(event.getCreateAt());
        int countValues = chartJSData.getLabels().size();
        if (chartJSData.getPrevDate().equals(stringDate)) {
            if (event.getType() == Event.Type.INCOME) {
                chartJSData.getDataIncome().set(countValues - 1, chartJSData.getDataIncome().get(countValues - 1).add(event.getAmount()));
            } else {
                chartJSData.getDataOutcome().set(countValues - 1, chartJSData.getDataOutcome().get(countValues - 1).add(event.getAmount()));
            }
            chartJSData.setPrevDate(stringDate);
        } else {
            chartJSData.getLabels().add(stringDate);
            chartJSData.setPrevDate(stringDate);
            if (event.getType() == Event.Type.INCOME) {
                chartJSData.getDataIncome().add(event.getAmount());
                chartJSData.getDataOutcome().add(BigDecimal.ZERO);
            } else {
                chartJSData.getDataIncome().add(BigDecimal.ZERO);
                chartJSData.getDataOutcome().add(event.getAmount());
            }
        }
    }

    @Getter
    @Setter
    class ChartJSData implements ChartData {

        @JsonIgnore
        private String prevDate = "";
        private List<String> labels = new ArrayList<>();
        private List<BigDecimal> dataIncome = new ArrayList<>();
        private List<BigDecimal> dataOutcome = new ArrayList<>();
        private List<BigDecimal> dataProfit = new ArrayList<>();
        private BigDecimal totalIncome = BigDecimal.ZERO;
        private BigDecimal totalOutcome = BigDecimal.ZERO;

        @Override
        public ChartJSData calculateData(ChartDataPeriod period) {
            this.calculateProfit();
            this.calculateByPeriod(period);
            this.calculateTotalIncomeAndTotalOutcome();
            return this;
        }

        private void calculateProfit() {
            for (int i = 0; i < this.labels.size(); i++) {
                this.dataProfit.add(this.dataIncome.get(i).subtract(this.getDataOutcome().get(i)));
            }
        }

        private void calculateByPeriod(ChartDataPeriod period) {
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
            for (int i = 0; i < this.labels.size(); i++) {
                String yearAndMonth = this.labels.get(i).substring(0, countCutSymbol);
                if (!yearAndMonth.equals(remainderMonth)) {
                    remainderMonth = yearAndMonth;
                } else {
                    this.getDataIncome().set(i, this.getDataIncome().get(i).add(this.getDataIncome().get(i - 1)));
                    this.getDataOutcome().set(i, this.getDataOutcome().get(i).add(this.getDataOutcome().get(i - 1)));
                    this.getDataProfit().set(i, this.getDataProfit().get(i).add(this.getDataProfit().get(i - 1)));
                }
            }
        }

        private void calculateTotalIncomeAndTotalOutcome() {
            BigDecimal totalIncome = this.getDataIncome().stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            BigDecimal totalOutCome = this.getDataOutcome().stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            this.setTotalIncome(totalIncome);
            this.setTotalOutcome(totalOutCome);
        }

    }
}

