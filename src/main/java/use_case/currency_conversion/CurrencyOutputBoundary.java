package use_case.currency_conversion;

public interface CurrencyOutputBoundary {
    void prepareSuccessView(CurrencyOutputData data);

    void prepareFailView(String error);
}
