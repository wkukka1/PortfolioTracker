package use_case.currency_conversion;

public class CurrencyOutputData {
    private final double exchangeRate;

    public String getNewCurrency() {
        return newCurrency;
    }

    private final String newCurrency;

    public CurrencyOutputData(double exchangeRate, String newCurrency) {
        this.exchangeRate = exchangeRate;
        this.newCurrency = newCurrency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }


}
