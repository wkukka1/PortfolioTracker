package use_case;

import entity.Investment;
import entity.Portfolio;

import java.util.NoSuchElementException;

public interface PortfolioDataAccessInterface {
    void savePortfolio(Portfolio currPortfolio);
    Portfolio getPortfolioByID(int userID);
    void addStockToPortfolioByID(int userID, Investment newStock, double stockProfitToDate) throws NoSuchElementException;
    void deletePortfolio(int id);
}
