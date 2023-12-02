package app;

import data_access.FilePortfolioDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeletePresenter;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.delete_user.DeleteViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.show.ShowController;
import interface_adapter.show.ShowPresenter;
import interface_adapter.show.ShowState;
import interface_adapter.show.ShowViewModel;
import use_case.delete_user.DeleteInputBoundary;
import use_case.delete_user.DeleteInteractor;
import use_case.delete_user.DeleteOutputBoundary;
import use_case.show.*;
import use_case.signup.SignupUserDataAccessInterface;
import view.LoggedInView;
import view.LoginView;
import view.validation.StockFieldValidator;

import javax.swing.*;
import java.io.IOException;

public class LoggedInUseCaseFactory {
    private LoggedInUseCaseFactory() {
    }

    public static LoggedInView create(LoggedInViewModel loggedInViewModel,
                                      LoginViewModel loginViewModel,
                                      ViewManagerModel viewManagerModel,
                                      SignupUserDataAccessInterface userDataAccessInterface,
                                      DeleteViewModel deleteViewModel,
                                      FilePortfolioDataAccessObject portfolioDataAccessObject,
                                      LoginView loginView,
                                      StockFieldValidator stockFieldValidator,
                                      ShowViewModel showViewModel,
                                      StockPriceDataAccessInterface stockDataAccessObject
                                      ) {
        try {
            DeleteController deleteController = createDeleteController(deleteViewModel, loginViewModel, viewManagerModel,
                    userDataAccessInterface, loggedInViewModel, portfolioDataAccessObject);

            ShowController showController = createShowController(showViewModel, viewManagerModel, portfolioDataAccessObject, stockDataAccessObject);

            DeleteState deleteState = new DeleteState();
            ShowState showState = new ShowState();
            return new LoggedInView(loggedInViewModel, deleteState, deleteController, loginView, stockFieldValidator, showState, showController);
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Could not open user data file");
        }
        return null;
    }

    private static DeleteController createDeleteController(DeleteViewModel deleteViewModel,
                                                           LoginViewModel loginViewModel,
                                                           ViewManagerModel viewManagerModel,
                                                           SignupUserDataAccessInterface userDataAccessInterface,
                                                           LoggedInViewModel loggedInViewModel,
                                                           FilePortfolioDataAccessObject portfolioDataAccessObject) throws IOException {
        DeleteOutputBoundary deleteOutputBoundary = new DeletePresenter(deleteViewModel, viewManagerModel,
                loggedInViewModel, loginViewModel);
        DeleteInputBoundary deleteInteractor = new DeleteInteractor(userDataAccessInterface, deleteOutputBoundary, portfolioDataAccessObject);
        return new DeleteController(deleteInteractor);
    }

    private static ShowController createShowController(ShowViewModel showViewModel,
                                                       ViewManagerModel viewManagerModel,
                                                       ShowPortfolioDataAccessInterface portfolioDataAccessObject,
                                                       StockPriceDataAccessInterface stockDataAccessObject) throws IOException {
        ShowOutputBoundary showPresenter = new ShowPresenter(showViewModel, viewManagerModel);
        ShowInputBoundary showInteractor = new ShowInteractor(portfolioDataAccessObject, stockDataAccessObject, showPresenter);
        return new ShowController(showInteractor);

    }
}
