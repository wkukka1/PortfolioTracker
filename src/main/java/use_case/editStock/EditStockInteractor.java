package use_case.editStock;

import entity.Investment;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.PortfolioDataAccessInterface;
import entity.User;
import entity.Stock;
import entity.Portfolio;

import java.util.Map;

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

            String username = editStock.getUsername();
            User user = userDataAccessObject.getUserFromUsername(username);
            int userId = user.getUserID();
            Portfolio portfolio = portfolioDataAccessObject.getPortfolioByID(userId);

            Investment stock = portfolio.getStockByTicker(tickerSymbol);
            stock.setQuantity(newQuantity);

            portfolioDataAccessObject.savePortfolio(portfolio); // Saves portfolio with updated quantity of the stock

            Map<String, Double> tickersToQuantities = portfolio.generateTickersToQuantities(); // Used in the outputdata to output the new quantity in the LoggedInView

            EditStockOutputData output = new EditStockOutputData(tickersToQuantities);
            editStockPresenter.prepareSuccessView(output);
        } catch (Exception e){
            editStockPresenter.prepareFailView(e.toString());
            System.out.println(e.toString());
        }
    }
}
