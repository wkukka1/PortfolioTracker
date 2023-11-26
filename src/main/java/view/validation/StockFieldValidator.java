package view.validation;

public interface StockFieldValidator {
    boolean isDateStrValid(String date);

    boolean isTickerStrValid(String ticker);

    boolean isAmountStrValid(String amount);
}
