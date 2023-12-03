package view;

import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import entity.CommonUserFactory;
import entity.Portfolio;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.login.*;
import use_case.signup.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LoginUsersTests {

    public void addUser(int numOfUsers) {
        UserFactory uf = new CommonUserFactory();
        FileUserDataAccessObject fudao;
        FilePortfolioDataAccessObject fpdao;
        try {
            fudao = new FileUserDataAccessObject("./users.csv", uf);
            fpdao = new FilePortfolioDataAccessObject("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < numOfUsers; i++) {
            fudao.save(uf.create("user" + i, "password" + i, LocalDateTime.now(), i));
            fpdao.savePortfolio(new Portfolio(new ArrayList<>(), 0, i));
        }
    }

    private boolean loginUser(String username, String password) throws IOException {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        UserFactory userFactory = new CommonUserFactory();
        try {
            FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("./users.csv", userFactory);
            PortfolioDataAccessInterface portfolioDataAccessInterface = new FilePortfolioDataAccessObject("./portfolios.csv");
            SignupOutputBoundary signupPresenter = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
            SignupInputBoundary signupInteractor = new SignupInteractor(userDataAccessObject, portfolioDataAccessInterface, signupPresenter, userFactory);
            SignupController signupController = new SignupController(signupInteractor);
            LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, loggedInViewModel, loginViewModel, new SignupView(signupController, signupViewModel));
            LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter);
            LoginController loginController = new LoginController(loginInteractor);
            loginController.execute(username, password);
            return loggedInViewModel.getLoggedInUser().equals(username);
        } catch (Exception e) {
            System.out.println("The file was not found");
        }
        return false;
    }


    @org.junit.Test
    public void loginOneUserTest() throws IOException {
        addUser(1);
        assert loginUser("user0", "password0");
    }

    @org.junit.Test
    public void loginMultipleUsersTest() throws IOException {
        addUser(5);
        assert loginUser("user0", "password0");
        assert loginUser("user1", "password1");
        assert loginUser("user2", "password2");
        assert loginUser("user3", "password3");
        assert loginUser("user4", "password4");
    }
}
