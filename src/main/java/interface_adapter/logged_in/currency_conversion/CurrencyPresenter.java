package interface_adapter.logged_in.currency_conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import use_case.currency_conversion.CurrencyInputBoundary;
import use_case.currency_conversion.CurrencyInputData;
import use_case.show.ShowInputData;

public class CurrencyPresenter {
    final CurrencyInputBoundary currencyUseCaseInteractor;


    public CurrencyPresenter(CurrencyInputBoundary currencyUseCaseInteractor) {
        this.currencyUseCaseInteractor = currencyUseCaseInteractor;
    }

    public void execute(String currencyFrom, String currencyTo) throws JsonProcessingException {
        CurrencyInputData currencyInputData = new CurrencyInputData(currencyFrom, currencyTo);
        currencyUseCaseInteractor.execute(currencyInputData);
    }
}
