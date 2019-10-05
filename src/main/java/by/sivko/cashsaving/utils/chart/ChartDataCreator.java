package by.sivko.cashsaving.utils.chart;

import by.sivko.cashsaving.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.stream.Stream;

public abstract class ChartDataCreator<T extends ChartData> {
    
    public T createChartData(Stream<Event> eventStream, ChartDataPeriod period) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        T chartData = getCreateData();
        eventStream.sorted(Comparator.comparing(Event::getCreateAt))
                .forEachOrdered(event -> {
                    calculateIncomeAndOutCome(format, event, chartData);
                });
        calculateProfit(chartData);
        calculateTotalIncomeAndTotalOutcome(chartData);
        calculateByPeriod(chartData, period);
        return chartData;
    }

    protected abstract T getCreateData();

    protected abstract void calculateByPeriod(T chartData, ChartDataPeriod period);

    protected abstract void calculateTotalIncomeAndTotalOutcome(T chartData);

    protected abstract void calculateProfit(T chartData);

    protected abstract void calculateIncomeAndOutCome(DateFormat format, Event event, T chartData);
}
