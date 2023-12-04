package interface_adapter.editStock;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.editStock.EditStockOutputBoundary;
import use_case.editStock.EditStockOutputData;

import javax.swing.*;

public class EditStockPresenter implements EditStockOutputBoundary {
    private final LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    public EditStockPresenter(LoggedInViewModel loggedInViewModel,
                              ViewManagerModel viewManagerModel){
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareSuccessView(EditStockOutputData stock){
        LoggedInState loggedInState = loggedInViewModel.getState();
        this.loggedInViewModel.setState(loggedInState);
        loggedInViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error){
        JOptionPane.showMessageDialog(null, "There was an error in editing the stock. " + error,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
