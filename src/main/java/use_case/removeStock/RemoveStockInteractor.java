package use_case.removeStock;

import java.util.List;
import java.util.Map;

import entity.Investment;
import use_case.PortfolioDataAccessInterface;
import entity.Portfolio;
import entity.User;

public class RemoveStockInteractor implements RemoveStockInputBoundary{
    final RemoveStockUserDataAccessInterface userDataAccessObject;
    final PortfolioDataAccessInterface portfolioDataAccessObject;
    final RemoveStockOutputBoundary removeStockPresenter;
    public RemoveStockInteractor(RemoveStockUserDataAccessInterface userDataAccessObject,
                                 PortfolioDataAccessInterface portfolioDataAccessObject,
                                 RemoveStockOutputBoundary removeStockPresenter){
        this.userDataAccessObject = userDataAccessObject;
        this.portfolioDataAccessObject = portfolioDataAccessObject;
        this.removeStockPresenter = removeStockPresenter;
    }

    /**
     * Removes the stock that's in the InputData from the user's list of stocks in their portfolio
     * @param removeStockInputData has the stock that the user want's to remove
     */
    public void execute(RemoveStockInputData removeStockInputData){
        try{
            double netProfit;

            String tickerSymbol = removeStockInputData.getTicker(); // Ticker of Stock that needs to be removed
            String username = removeStockInputData.getUsername();
            User user = userDataAccessObject.getUserFromUsername(username);
            int userId = user.getUserID();
            Portfolio portfolio = portfolioDataAccessObject.getPortfolioByID(userId); // Current user's portfolio
            List<Investment> stockList = portfolio.getStockList();

            Investment stock = portfolio.getStockByTicker(tickerSymbol); // Stock to be removed
            stockList.remove(stock); // Removes the stock

            portfolio.setStockList(stockList); // Updates the stock list

            Map<String, Double> tickersToQuantities = portfolio.generateTickersToQuantities();

            netProfit = portfolio.getNetProfit(); // Updated net profit

            portfolioDataAccessObject.savePortfolio(portfolio); // Saves user's portfolio to the database

            RemoveStockOutputData outputData = new RemoveStockOutputData(netProfit, tickersToQuantities);
            removeStockPresenter.prepareSuccessView(outputData);
        } catch (Exception e){
            removeStockPresenter.prepareFailView(e.toString());
            System.out.println(e.toString());
        }

    }
}
