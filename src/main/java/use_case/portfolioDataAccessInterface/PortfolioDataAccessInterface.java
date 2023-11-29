package use_case.portfolioDataAccessInterface;

import entity.Portfolio;

public interface PortfolioDataAccessInterface {
    void savePortfolio(Portfolio currPortfolio);
    Portfolio getPortfolioByID(int userID);
}
