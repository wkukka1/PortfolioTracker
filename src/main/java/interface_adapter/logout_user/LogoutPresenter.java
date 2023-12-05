package interface_adapter.logout_user;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.logout.LogoutOutputBoundary;


import javax.swing.*;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private ViewManagerModel viewManagerModel;

    public LogoutPresenter(LoginViewModel loginViewModel, LoggedInViewModel loggedInViewModel
            , ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(){
        LoggedInState loggedInState = loggedInViewModel.getState();

        loggedInState.setUsername(null);
        this.loggedInViewModel.setState(loggedInState);
        this.loggedInViewModel.setLoggedInUser(null);
        this.loggedInViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(loginViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView() {
        JFrame frame = new JFrame("Logout Error");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JOptionPane with an error message
        JOptionPane.showMessageDialog(frame,
                "An error occurred while logging out.",
                "Logout Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
