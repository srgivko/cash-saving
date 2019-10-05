package by.sivko.cashsaving.utils.chart.chartjs;

import by.sivko.cashsaving.models.Event;
import by.sivko.cashsaving.utils.chart.ChartDataCreator;
import by.sivko.cashsaving.utils.chart.ChartDataPeriod;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DateFormat;

@Component
public class ChartJSChartDataCreatorImpl extends ChartDataCreator<ChartJSData> {

    @Override
    protected ChartJSData getCreateData() {
        return new ChartJSData();
    }

    @Override
    protected void calculateIncomeAndOutCome(DateFormat format, Event event, ChartJSData chartJSData) {
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

    @Override
    protected void calculateProfit(ChartJSData chartJSData) {
        for (int i = 0; i < chartJSData.getLabels().size(); i++) {
            chartJSData.getDataProfit().add(chartJSData.getDataIncome().get(i).subtract(chartJSData.getDataOutcome().get(i)));
        }
    }

    @Override
    protected void calculateByPeriod(ChartJSData chartJSData, ChartDataPeriod period) {
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
        for (int i = 0; i < chartJSData.getLabels().size(); i++) {
            String yearAndMonth = chartJSData.getLabels().get(i).substring(0, countCutSymbol);
            if (!yearAndMonth.equals(remainderMonth)) {
                remainderMonth = yearAndMonth;
            } else {
                chartJSData.getDataIncome().set(i, chartJSData.getDataIncome().get(i).add(chartJSData.getDataIncome().get(i - 1)));
                chartJSData.getDataOutcome().set(i, chartJSData.getDataOutcome().get(i).add(chartJSData.getDataOutcome().get(i - 1)));
                chartJSData.getDataProfit().set(i, chartJSData.getDataProfit().get(i).add(chartJSData.getDataProfit().get(i - 1)));
            }
        }
    }

    @Override
    protected void calculateTotalIncomeAndTotalOutcome(ChartJSData chartJSData) {
        BigDecimal totalIncome = chartJSData.getDataIncome().stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        BigDecimal totalOutCome = chartJSData.getDataOutcome().stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        chartJSData.setTotalIncome(totalIncome);
        chartJSData.setTotalOutcome(totalOutCome);
    }
}

