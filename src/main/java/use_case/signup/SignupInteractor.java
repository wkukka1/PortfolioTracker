package use_case.signup;

import entity.Portfolio;
import entity.User;
import entity.UserFactory;
import use_case.portfolioDataAccessInterface.PortfolioDataAccessInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SignupInteractor implements SignupInputBoundary {
    final SignupUserDataAccessInterface userDataAccessObject;
    final PortfolioDataAccessInterface portfolioDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            PortfolioDataAccessInterface portfolioDataAccessObject,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.portfolioDataAccessObject = portfolioDataAccessObject;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        } else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        } else {
            LocalDateTime now = LocalDateTime.now();
            User user = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword(), now, now.hashCode());
            userDataAccessObject.save(user);

            Portfolio initialPortfolio = new Portfolio(new ArrayList<>(), 0L, user.getUserID());
            portfolioDataAccessObject.savePortfolio(initialPortfolio);

            SignupOutputData signupOutputData = new SignupOutputData(user.getName(), now.toString(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }
}