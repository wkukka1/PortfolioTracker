package app;

import data_access.FilePortfolioDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeletePresenter;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.delete_user.DeleteViewModel;
import interface_adapter.editStock.EditStockController;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import use_case.delete_user.DeleteInputBoundary;
import use_case.delete_user.DeleteInteractor;
import use_case.delete_user.DeleteOutputBoundary;
import use_case.delete_user.DeleteUserDataAccessInterface;
import use_case.editStock.EditStockInputBoundary;
import use_case.editStock.EditStockOutputBoundary;
import use_case.editStock.EditStockUserDataAccessInterface;
import interface_adapter.editStock.EditStockPresenter;
import use_case.editStock.EditStockInteractor;
import use_case.signup.SignupUserDataAccessInterface;
import view.LoggedInView;
import view.LoginView;
import view.ViewManager;
import view.validation.StockFieldValidator;

import javax.swing.*;
import java.io.IOException;

public class LoggedInUseCaseFactory {
    private LoggedInUseCaseFactory() {
    }

    public static LoggedInView create(LoggedInViewModel loggedInViewModel,
                                      LoginViewModel loginViewModel,
                                      ViewManagerModel viewManagerModel
            , SignupUserDataAccessInterface userDataAccessInterface,
                                      DeleteViewModel deleteViewModel,
                                      FilePortfolioDataAccessObject portfolioDataAccessObject, LoginView loginView, StockFieldValidator stockFieldValidator,
                                      EditStockUserDataAccessInterface editStockUserDataAccessInterface) {
        try {
            DeleteController deleteController = createDeleteController(deleteViewModel, loginViewModel, viewManagerModel,
                    userDataAccessInterface, loggedInViewModel, portfolioDataAccessObject);
            DeleteState deleteState = new DeleteState();
            EditStockController editStockController = createEditStockController(viewManagerModel, loggedInViewModel, editStockUserDataAccessInterface, portfolioDataAccessObject);
            return new LoggedInView(loggedInViewModel, deleteState, deleteController, loginView, stockFieldValidator, editStockController);
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

    private static EditStockController createEditStockController(ViewManagerModel viewManagerModel,
                                                                 LoggedInViewModel loggedInViewModel,
                                                                 EditStockUserDataAccessInterface userDataAccessInterface,
                                                                 FilePortfolioDataAccessObject portfolioDataAccessObject){
        //Output boundary
        EditStockOutputBoundary editStockOutputBoundary = new EditStockPresenter(loggedInViewModel, viewManagerModel);

        //Input boundary
        EditStockInputBoundary editStockInteractor = new EditStockInteractor(loggedInViewModel, editStockOutputBoundary, portfolioDataAccessObject, userDataAccessInterface);

        return new EditStockController(editStockInteractor);
    }
}
