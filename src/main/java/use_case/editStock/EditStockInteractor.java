package use_case.editStock;

import interface_adapter.logged_in.LoggedInViewModel;
import use_case.signup.PortfolioDataAccessInterface;
import entity.User;
import entity.Stock;
import entity.Portfolio;

public class EditStockInteractor implements EditStockInputBoundary {
    final LoggedInViewModel loggedInViewModel;
    final EditStockOutputBoundary editStockPresenter;
    final PortfolioDataAccessInterface portfolioDataAccessObject;
    final EditStockUserDataAccessInterface userDataAccessObject;

    public EditStockInteractor(LoggedInViewModel loggedInViewModel,
                               EditStockOutputBoundary editStockPresenter,
                               PortfolioDataAccessInterface portfolioDataAccessInterface,
                               EditStockUserDataAccessInterface userDataAccessInterface){
        this.loggedInViewModel = loggedInViewModel;
        this.editStockPresenter = editStockPresenter;
        this.portfolioDataAccessObject = portfolioDataAccessInterface;
        this.userDataAccessObject = userDataAccessInterface;
    }

    /**
     * Edits how many shares of a stock the user owns
     * @param editStock Contains the Stock the user wants to change and the new amount
     *              of shares they own in that stock.
     */
    public void execute(EditStockInputData editStock){
        try {
            String tickerSymbol = editStock.getTickerSymbol();
            double newQuantity = editStock.getNewQuantity();

            String username = loggedInViewModel.getLoggedInUser();
            User user = userDataAccessObject.get(username);
            int userId = user.getUserID();
            Portfolio portfolio = portfolioDataAccessObject.getPortfolioByID(userId);

            Stock stock = portfolio.getStockByTicker(tickerSymbol);
            stock.setQuantity(newQuantity);
            editStockPresenter.prepareSuccessView();
        } catch (Exception e){
            editStockPresenter.prepareFailView(e.toString());
            System.out.println(e.toString());
        }
    }
}
