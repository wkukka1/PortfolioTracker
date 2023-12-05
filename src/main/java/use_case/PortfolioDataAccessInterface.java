package use_case;

import entity.Portfolio;
import entity.Stock;

public interface PortfolioDataAccessInterface {
    void savePortfolio(Portfolio currPortfolio);
    Portfolio getPortfolioByID(int userID);
    void addStockToPortfolioByID(int userID, Stock newStock, double stockProfitToDate);
}
