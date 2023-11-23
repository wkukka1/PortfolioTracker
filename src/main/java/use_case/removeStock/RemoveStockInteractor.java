package use_case.removeStock;

import java.util.List;
import use_case.portfolioDataAccessInterface.PortfolioDataAccessInterface;
import entity.Portfolio;
import entity.Stock;

public class RemoveStockInteractor implements RemoveStockInputBoundary{
    final RemoveStockUserDataAccessInterface userDataAccessObject;
    final PortfolioDataAccessInterface portfolioDataAccessInterface;
    final RemoveStockOutputBoundary removeStockPresenter;
    public RemoveStockInteractor(){

    }

    public void execute(RemoveStockInputData removeStockInputData){
        String tickerNumber = removeStockInputData.getTickerNumber();
        int userId = userDataAccessObject.getUserId();
        Portfolio portfolio = portfolioDataAccessInterface.getPortfolioByID(userId);
        List<Stock> stockList = portfolio.getStockList();

        stockList.remove(tickerNumber);
        portfolio.setStockList(stockList);

        removeStockPresenter.prepareView();
    }
}
