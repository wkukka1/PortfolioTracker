package interface_adapter.editStock;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.editStock.EditStockOutputBoundary;

import javax.swing.*;

public class EditStockPresenter implements EditStockOutputBoundary {
    private final EditStockViewModel editStockViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    public EditStockPresenter(EditStockViewModel editStockViewModel,
                              LoggedInViewModel loggedInViewModel,
                              ViewManagerModel viewManagerModel){
        this.editStockViewModel = editStockViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void prepareSuccessView(){
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
