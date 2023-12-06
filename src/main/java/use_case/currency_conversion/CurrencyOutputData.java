package use_case.currency_conversion;

import org.jfree.chart.ChartPanel;

public class CurrencyOutputData {
    private final double exchangeRate;

    public CurrencyOutputData(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }


}
