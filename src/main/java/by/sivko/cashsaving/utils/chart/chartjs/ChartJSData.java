package by.sivko.cashsaving.utils.chart.chartjs;

import by.sivko.cashsaving.utils.chart.ChartData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChartJSData extends ChartData {
    @JsonIgnore
    private String prevDate = "";
    private List<String> labels = new ArrayList<>();
    private List<BigDecimal> dataIncome = new ArrayList<>();
    private List<BigDecimal> dataOutcome = new ArrayList<>();
    private List<BigDecimal> dataProfit = new ArrayList<>();
    private BigDecimal totalIncome = BigDecimal.ZERO;
    private BigDecimal totalOutcome = BigDecimal.ZERO;
}
