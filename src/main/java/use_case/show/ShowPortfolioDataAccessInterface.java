package use_case.show;

import entity.Portfolio;

public interface ShowPortfolioDataAccessInterface {
    void savePortfolio(Portfolio currPortfolio);
    Portfolio getPortfolioByID(int userID);
}
