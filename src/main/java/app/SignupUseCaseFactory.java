package app;

import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.PortfolioDataAccessInterface;
import use_case.signup.*;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.*;
import view.SignupView;

import javax.swing.*;
import java.io.IOException;

public class SignupUseCaseFactory {

    /**
     * Prevent instantiation.
     */
    private SignupUseCaseFactory() {
    }

    public static SignupView create(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel,
                                    SignupViewModel signupViewModel, SignupUserDataAccessInterface userDataAccessObject,
                                    PortfolioDataAccessInterface portfolioDataAccessObject) {
        try {
            SignupController signupController = createUserSignupUseCase(viewManagerModel, signupViewModel,
                    loginViewModel, userDataAccessObject, portfolioDataAccessObject);
            return new SignupView(signupController, signupViewModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }



    private static SignupController createUserSignupUseCase(ViewManagerModel viewManagerModel, SignupViewModel signupViewModel,
                                                            LoginViewModel loginViewModel, SignupUserDataAccessInterface userDataAccessObject,
                                                            PortfolioDataAccessInterface portfolioDataAccessObject) throws IOException {
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
        UserFactory userFactory = new CommonUserFactory();

        SignupInputBoundary userSignupInteractor = new SignupInteractor(userDataAccessObject, portfolioDataAccessObject,
                signupOutputBoundary, userFactory);

        return new SignupController(userSignupInteractor);
    }
}
