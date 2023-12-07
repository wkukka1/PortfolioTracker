package use_case.signup;

import entity.Investment;
import entity.Portfolio;
import entity.Stock;

import java.util.NoSuchElementException;

public interface PortfolioDataAccessInterface {
    void savePortfolio(Portfolio currPortfolio);
    Portfolio getPortfolioByID(int userID);
    void addStockToPortfolioByID(int userID, Investment newStock, double stockProfitToDate) throws NoSuchElementException;
    void deletePortfolio(int id);
}
