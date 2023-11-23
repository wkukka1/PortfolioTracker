package use_case.removeStock;

import java.util.List;
import use_case.portfolioDataAccessInterface.PortfolioDataAccessInterface;
import entity.Portfolio;
import entity.Stock;

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

    public void execute(RemoveStockInputData removeStockInputData){
        String tickerNumber = removeStockInputData.getTickerNumber();
        int userId = userDataAccessObject.getUserId();
        Portfolio portfolio = portfolioDataAccessObject.getPortfolioByID(userId);
        List<Stock> stockList = portfolio.getStockList();

        stockList.remove(tickerNumber);
        portfolio.setStockList(stockList);

        removeStockPresenter.prepareView();
    }
}
