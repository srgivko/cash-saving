package by.sivko.cashsaving.utils.chart;

import by.sivko.cashsaving.models.Event;

import java.util.stream.Stream;

public interface ChartDataCreator {
    ChartData createChartData(Stream<Event> stream, ChartDataPeriod chartDataPeriod);
}
