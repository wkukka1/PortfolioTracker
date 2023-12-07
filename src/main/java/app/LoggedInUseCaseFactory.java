package app;

import data_access.ExchangeRateClient;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logged_in.add_stock.AddStockController;
import interface_adapter.logged_in.add_stock.AddStockPresenter;
import interface_adapter.removeStock.RemoveStockController;
import interface_adapter.removeStock.RemoveStockPresenter;
import interface_adapter.logged_in.currency_conversion.CurrencyController;
import interface_adapter.logged_in.currency_conversion.CurrencyPresenter;
import use_case.add_stock.AddStockInputBoundary;
import use_case.add_stock.AddStockInteractor;
import use_case.add_stock.AddStockOutputBoundary;
import use_case.add_stock.StockCalculationService;
import use_case.removeStock.RemoveStockInputBoundary;
import use_case.removeStock.RemoveStockInteractor;
import use_case.removeStock.RemoveStockOutputBoundary;
import use_case.removeStock.RemoveStockUserDataAccessInterface;
import use_case.currency_conversion.CurrencyDataAccessInterface;
import use_case.currency_conversion.CurrencyInputBoundary;
import use_case.currency_conversion.CurrencyInteractor;
import use_case.currency_conversion.CurrencyOutputBoundary;
import use_case.show.StockPriceDataAccessInterface;
import use_case.PortfolioDataAccessInterface;
import view.LoggedInView;
import view.validation.StockFieldValidator;
import data_access.FilePortfolioDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeletePresenter;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.delete_user.DeleteViewModel;
import interface_adapter.editStock.EditStockController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logged_in.show.ShowController;
import interface_adapter.logged_in.show.ShowPresenter;
import use_case.delete_user.DeleteInputBoundary;
import use_case.delete_user.DeleteInteractor;
import use_case.delete_user.DeleteOutputBoundary;
import use_case.editStock.EditStockInputBoundary;
import use_case.editStock.EditStockOutputBoundary;
import use_case.editStock.EditStockUserDataAccessInterface;
import interface_adapter.editStock.EditStockPresenter;
import use_case.editStock.EditStockInteractor;
import use_case.show.*;
import interface_adapter.logout_user.LogoutController;
import interface_adapter.logout_user.LogoutPresenter;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;
import view.LoginView;
import view.validation.StockFieldValidatorImpl;

import javax.swing.*;
import java.io.IOException;

public class LoggedInUseCaseFactory {
    private LoggedInUseCaseFactory() {
    }

    public static LoggedInView create(JFrame appFrame, LoggedInViewModel loggedInViewModel,
                                      LoginViewModel loginViewModel,
                                      ViewManagerModel viewManagerModel,
                                      SignupUserDataAccessInterface userDataAccessInterface,
                                      DeleteViewModel deleteViewModel,
                                      FilePortfolioDataAccessObject portfolioDataAccessObject,
                                      StockPriceDataAccessInterface stockDataAccessObject, LoginView loginView,
                                      StockCalculationService stockCalculationServiceImpl, EditStockUserDataAccessInterface editStockUserDataAccessInterface,
                                      RemoveStockUserDataAccessInterface removeStockUserDataAccessObject) {
        try {
            DeleteController deleteController = createDeleteController(deleteViewModel, loginViewModel, viewManagerModel,
                    userDataAccessInterface, loggedInViewModel, portfolioDataAccessObject);

            ShowController showController = createShowController(loggedInViewModel, portfolioDataAccessObject, stockDataAccessObject);

            DeleteState deleteState = new DeleteState();
            AddStockController addStockController = createAddStockUseCase(stockDataAccessObject,
                    portfolioDataAccessObject, loggedInViewModel, stockCalculationServiceImpl);
            StockFieldValidator stockFieldValidator = new StockFieldValidatorImpl();

            EditStockController editStockController = createEditStockController(loggedInViewModel, editStockUserDataAccessInterface, portfolioDataAccessObject);

            LogoutController logoutController = createLogoutController(loginViewModel, loggedInViewModel, viewManagerModel);

            RemoveStockController removeStockController = createRemoveStockUseCase(viewManagerModel, loggedInViewModel, removeStockUserDataAccessObject, portfolioDataAccessObject);
            CurrencyController currencyController = createCurrentyController(loggedInViewModel);
            return new LoggedInView(appFrame, loggedInViewModel, deleteState, deleteController, loginView, stockFieldValidator, addStockController, logoutController, showController, editStockController, currencyController, removeStockController);
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "Could not open user data file");
        }
        return null;
    }

    private static CurrencyController createCurrentyController(LoggedInViewModel loggedInViewModel) {
        CurrencyOutputBoundary currencyPresenter = new CurrencyPresenter(loggedInViewModel);
        CurrencyDataAccessInterface currencyClient = new ExchangeRateClient();
        CurrencyInputBoundary currencyInteractor = new CurrencyInteractor(currencyClient, currencyPresenter);
        return new CurrencyController(currencyInteractor);
    }

    private static LogoutController createLogoutController(LoginViewModel loginViewModel, LoggedInViewModel loggedInViewModel, ViewManagerModel viewManagerModel) {
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(loginViewModel, loggedInViewModel, viewManagerModel);
        LogoutInputBoundary logoutInteractor = new LogoutInteractor(logoutPresenter);
        return new LogoutController(logoutInteractor);
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

    private static EditStockController createEditStockController(LoggedInViewModel loggedInViewModel,
                                                                 EditStockUserDataAccessInterface userDataAccessInterface,
                                                                 FilePortfolioDataAccessObject portfolioDataAccessObject) {
        //Output boundary
        EditStockOutputBoundary editStockOutputBoundary = new EditStockPresenter(loggedInViewModel);

        //Input boundary
        EditStockInputBoundary editStockInteractor = new EditStockInteractor(loggedInViewModel, editStockOutputBoundary, portfolioDataAccessObject, userDataAccessInterface);

        return new EditStockController(editStockInteractor);
    }

    private static ShowController createShowController(LoggedInViewModel loggedInViewModel,
                                                       ShowPortfolioDataAccessInterface portfolioDataAccessObject,
                                                       StockPriceDataAccessInterface stockDataAccessObject) throws IOException {
        ShowOutputBoundary showPresenter = new ShowPresenter(loggedInViewModel);
        ShowInputBoundary showInteractor = new ShowInteractor(portfolioDataAccessObject, stockDataAccessObject, showPresenter);
        return new ShowController(showInteractor);
    }

    private static AddStockController createAddStockUseCase(StockPriceDataAccessInterface stockPriceClientImpl,
                                                            PortfolioDataAccessInterface portfolioDataAccessObject,
                                                            LoggedInViewModel loggedInViewModel,
                                                            StockCalculationService stockCalculationServiceImpl) {
        AddStockOutputBoundary addStockPresenter = new AddStockPresenter(loggedInViewModel);
        AddStockInputBoundary addStockInteractor = new AddStockInteractor(stockPriceClientImpl,
                portfolioDataAccessObject, addStockPresenter, stockCalculationServiceImpl);
        return new AddStockController(addStockInteractor);
    }

    private static RemoveStockController createRemoveStockUseCase(ViewManagerModel viewManagerModel,
                                                                  LoggedInViewModel loggedInViewModel,
                                                                  RemoveStockUserDataAccessInterface userDataAccessObject,
                                                                  PortfolioDataAccessInterface portfolioDataAccessObject){
        RemoveStockOutputBoundary removeStockPresenter = new RemoveStockPresenter(viewManagerModel, loggedInViewModel);
        RemoveStockInputBoundary removeStockInteractor = new RemoveStockInteractor(userDataAccessObject, portfolioDataAccessObject, removeStockPresenter);
        return new RemoveStockController(removeStockInteractor);
    }
}
