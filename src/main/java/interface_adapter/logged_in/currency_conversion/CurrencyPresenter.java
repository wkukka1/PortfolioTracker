package interface_adapter.logged_in.currency_conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.currency_conversion.CurrencyInputBoundary;
import use_case.currency_conversion.CurrencyInputData;
import use_case.currency_conversion.CurrencyOutputBoundary;
import use_case.currency_conversion.CurrencyOutputData;
import use_case.show.ShowInputData;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyPresenter implements CurrencyOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    public CurrencyPresenter(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(CurrencyOutputData data) {
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setNetProfit(round(Double.valueOf(loggedInState.getNetProfit()) * data.getExchangeRate(), 2));
        loggedInState.setCurrentCurrency(data.getNewCurrency());
        loggedInViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

    }

    private double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
