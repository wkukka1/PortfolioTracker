package interface_adapter.editStock;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.editStock.EditStockOutputBoundary;
import use_case.editStock.EditStockOutputData;

import javax.swing.*;

public class EditStockPresenter implements EditStockOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;

    public EditStockPresenter(LoggedInViewModel loggedInViewModel){
        this.loggedInViewModel = loggedInViewModel;
    }
    @Override
    public void prepareSuccessView(EditStockOutputData outputData){
        LoggedInState loggedInState = loggedInViewModel.getState();
        this.loggedInViewModel.setState(loggedInState);
        loggedInState.setTickersToAggregatedQuantities(outputData.getTickersToAggregatedQuantities());
        loggedInViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error){
        JOptionPane.showMessageDialog(null, "There was an error in editing the stock",
                "Edit Stock Error", JOptionPane.ERROR_MESSAGE);
    }
}
