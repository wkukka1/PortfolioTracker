package use_case.show;

import entity.Portfolio;

public interface PortfolioDataAccessInterface {
    void savePortfolio(Portfolio currPortfolio);
    Portfolio getPortfolioByID(int userID);
}
