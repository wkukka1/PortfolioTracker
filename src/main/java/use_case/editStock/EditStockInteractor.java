package use_case.editStock;

import interface_adapter.logged_in.LoggedInViewModel;
import use_case.signup.PortfolioDataAccessInterface;
import entity.User;
import entity.Stock;
import entity.Portfolio;

public class EditStockInteractor implements EditStockInputBoundary {
    final LoggedInViewModel loggedInViewModel;
    final EditStockOutputBoundary editStockPresenter;
    final PortfolioDataAccessInterface portfolioDataAccessInterface;
    final EditStockUserDataAccessInterface userDataAccessInterface;

    public EditStockInteractor(LoggedInViewModel loggedInViewModel,
                               EditStockOutputBoundary editStockPresenter,
                               PortfolioDataAccessInterface portfolioDataAccessInterface,
                               EditStockUserDataAccessInterface userDataAccessInterface){
        this.loggedInViewModel = loggedInViewModel;
        this.editStockPresenter = editStockPresenter;
        this.portfolioDataAccessInterface = portfolioDataAccessInterface;
        this.userDataAccessInterface = userDataAccessInterface;
    }


}
