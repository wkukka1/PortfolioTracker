package interface_adapter.login;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.ViewManagerModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import view.SignupView;

import javax.swing.*;

public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    private SignupView signupView;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel, SignupView signupView) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.signupView = signupView;
    }

    /**
     * @param response Takes in the response and switches the users screens from the login page to the home screen where they can look
     *                 at their portfolio
     */
    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the logged in view.

        LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        loggedInState.setUserID(response.getUserID());
        loggedInState.setNetProfit(response.getNetProfit());
        loggedInState.setTickersToAggregatedQuantities(response.getTickersToQuantities());
        loggedInState.setCurrentCurrency("USD");

        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.setLoggedInUser(response.getUsername());
        this.loggedInViewModel.firePropertyChanged();

        this.loginViewModel.setState(new LoginState());

        this.viewManagerModel.setActiveView(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setUsernameError(error);
        loginViewModel.firePropertyChanged();
    }


    @Override
    public void setUpSignUp() {
        this.viewManagerModel.setActiveView(this.signupView.viewName);
        this.viewManagerModel.firePropertyChanged();
    }

    public LoginState getLoginState() {
        return loginViewModel.getState();
    }


}
