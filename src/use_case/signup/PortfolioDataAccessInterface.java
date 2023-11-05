package use_case.signup;

import entity.Portfolio;

public interface PortfolioDataAccessInterface {
    void savePortfolio();
    Portfolio getPortfolioByID(int userID);
}
