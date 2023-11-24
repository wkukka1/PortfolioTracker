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
    private final RemoveStockViewModel removeStockViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    public RemoveStockPresenter(ViewManagerModel viewManagerModel,
                                RemoveStockViewModel removeStockViewModel,
                                LoggedInViewModel loggedInViewModel){
        this.viewManagerModel = viewManagerModel;
        this.removeStockViewModel = removeStockViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }
    @Override
    public void prepareSuccessView(RemoveStockOutputData response){
        LoggedInState loggedInState = loggedInViewModel.getState();
        this.loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
    @Override
    public void prepareFailView(String error){
        JOptionPane.showMessageDialog(null, "There was an error in deleting your account",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
