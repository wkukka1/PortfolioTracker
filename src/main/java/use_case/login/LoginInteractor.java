package use_case.login;

import entity.Investment;
import entity.Portfolio;
import entity.User;
import use_case.PortfolioDataAccessInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class LoginInteractor implements LoginInputBoundary {
    final LoginUserDataAccessInterface userDataAccessObject;
    private final PortfolioDataAccessInterface portfolioDataAccessImpl;
    final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary,
                           PortfolioDataAccessInterface portfolioDataAccessImpl) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
        this.portfolioDataAccessImpl = portfolioDataAccessImpl;
    }

    public void signup() {
        loginPresenter.setUpSignUp();
    }


    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        //checks if the username is not in the csv file
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        } else {
            String pwd = userDataAccessObject.getUserFromUsername(username).getPassword();
            //checks if the password on the csv file does not match the password the user inputted
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for " + username + ".");
            } else {

                User user = userDataAccessObject.getUserFromUsername(loginInputData.getUsername());
                Portfolio currPortfolio = portfolioDataAccessImpl.getPortfolioByID(user.getUserID());
                double overallNetProfit = currPortfolio.getNetProfit();

                LoginOutputData loginOutputData = new LoginOutputData(user.getName(), user.getUserID(),
                        false, round(overallNetProfit, 2),
                        generateTickersToQuantities(currPortfolio));
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

    private double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private Map<String, Double> generateTickersToQuantities(Portfolio currPortfolio) {
        Map<String, Double> tickersToQuantities = new HashMap<>();
        for (Investment stock : currPortfolio.getStockList()) {
            tickersToQuantities.put(stock.getTickerSymbol(), tickersToQuantities.getOrDefault(stock.getTickerSymbol(),
                    0.0) + stock.getQuantity());
        }
        tickersToQuantities.replaceAll((ticker, quantity) -> round(quantity, 2));
        return tickersToQuantities;
    }
}