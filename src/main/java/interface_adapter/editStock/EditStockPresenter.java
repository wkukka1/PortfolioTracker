package interface_adapter.editStock;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.editStock.EditStockOutputBoundary;

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

}
