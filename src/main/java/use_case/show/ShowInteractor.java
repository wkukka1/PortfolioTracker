package use_case.show;


import entity.Portfolio;
import entity.Stock;

import java.time.LocalDateTime;
import java.util.List;
import org.json.JSONObject;

public class ShowInteractor implements ShowInputBoundary{
    final PortfolioDataAccessInterface portfolioDataAccessObject;
    final StockPriceDataAccessInterface stockDataAccessObject;
    final ShowOutputBoundary showPresenter;

    public ShowInteractor(PortfolioDataAccessInterface portfolioDataAccessObject, StockPriceDataAccessInterface stockDataAccessObject, ShowOutputBoundary showPresenter) {
        this.portfolioDataAccessObject = portfolioDataAccessObject;
        this.stockDataAccessObject = stockDataAccessObject;
        this.showPresenter = showPresenter;
    }


    @Override
    public void execute(ShowInputData showInputData) {
        LocalDateTime now = LocalDateTime.now();
        Portfolio portfolio = portfolioDataAccessObject.getPortfolioByID(showInputData.getUserID());
        List<Stock> stockList = portfolio.getStockList();
        for(Stock stock : stockList) {
            // JSONObject stockInfo = stockDataAccessObject.getStockInfo(stock);
        }

    }

    private void plot() {

    }
}
