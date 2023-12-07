package interface_adapter.logged_in.add_stock;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.add_stock.AddStockOutputBoundary;
import use_case.add_stock.AddStockOutputData;


public class AddStockPresenter implements AddStockOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    public AddStockPresenter(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(AddStockOutputData addStockOutputData) {
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setNetProfit(addStockOutputData.getNewStockProfitToDate());
        loggedInState.setTickersToAggregatedQuantities(addStockOutputData.getTickersToQuantities());
        loggedInState.setAddStockError(null);
        loggedInState.setCurrentCurrency("USD");
        loggedInViewModel.firePropertyChanged();
    }

    @Override
    public void prepareNonSuccessView(String addStockErrorMsg) {
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setAddStockError(addStockErrorMsg);
        loggedInViewModel.firePropertyChanged();
    }
}
