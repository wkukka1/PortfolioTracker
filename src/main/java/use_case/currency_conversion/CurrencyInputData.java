package use_case.currency_conversion;

public class CurrencyInputData {
    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    final private String currencyFrom;
    final private String currencyTo;

    public CurrencyInputData(String currencyFrom, String currencyTo) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }
}
