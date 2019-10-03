package by.sivko.cashsaving.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CategoryStat {
    private BigDecimal income = BigDecimal.ZERO;
    private BigDecimal outcome = BigDecimal.ZERO;

    public void addToIncome(BigDecimal number) {
        this.income = this.income.add(number);
    }

    public void addToOutcome(BigDecimal number) {
        this.outcome = this.outcome.add(number);
    }

    public BigDecimal getTotal() {
        return this.income.subtract(outcome);
    }
}
