package interface_adapter.logged_in.currency_conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.currency_conversion.CurrencyInputBoundary;
import use_case.currency_conversion.CurrencyInputData;
import use_case.currency_conversion.CurrencyOutputBoundary;
import use_case.currency_conversion.CurrencyOutputData;
import use_case.show.ShowInputData;

public class CurrencyPresenter implements CurrencyOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    public CurrencyPresenter(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(CurrencyOutputData data) {
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setNetProfit(Double.valueOf(loggedInState.getNetProfit()) * data.getExchangeRate());
    }

    @Override
    public void prepareFailView(String error) {

    }
}
