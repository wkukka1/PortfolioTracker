package interface_adapter.removeStock;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.removeStock.RemoveStockOutputBoundary;
import interface_adapter.ViewManagerModel;
import use_case.removeStock.RemoveStockOutputBoundary;
import use_case.removeStock.RemoveStockOutputData;
import view.LoggedInView;

import javax.swing.*;

public class RemoveStockPresenter implements RemoveStockOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    public RemoveStockPresenter(ViewManagerModel viewManagerModel,
                                LoggedInViewModel loggedInViewModel){
        this.loggedInViewModel = loggedInViewModel;
    }

    /**
     * Loads the user back to the Logged In screen, after the program successfully removed the stock
     * @param response The output data after executing the interactor. Includes the updated list of stocks.
     */
    @Override
    public void prepareSuccessView(RemoveStockOutputData response){
        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setNetProfit(response.getNewStockProfitToDate());
        loggedInState.setTickersToAggregatedQuantities(response.getTickersToQuantities());
        loggedInViewModel.firePropertyChanged();
    }

    /**
     * Loads a fail message if something went wrong during in the interactor.
     * @param error The error message that will be displayed for the user
     */
    @Override
    public void prepareFailView(String error){
        JOptionPane.showMessageDialog(null, "There was an error in removing the stock.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
