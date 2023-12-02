package app;

import com.fasterxml.jackson.core.JsonProcessingException;
import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.StockInfoClient;
import data_access.StockInfoClient;
import entity.CommonUserFactory;

import interface_adapter.delete_user.DeleteViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.show.ShowViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import org.json.JSONObject;
import use_case.show.ShowInteractor;
import use_case.show.StockPriceDataAccessInterface;
import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;
import view.validation.StockFieldValidatorImpl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    private static final String PORTFOLIOS_CSV_FILE_PATH = "./portfolios.csv";

    public static void main(String[] args) throws JsonProcessingException {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        DeleteViewModel deleteViewModel = new DeleteViewModel();
        ShowViewModel showViewModel = new ShowViewModel();

        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FilePortfolioDataAccessObject portfolioDataAccessObject;
        try {
            portfolioDataAccessObject = new FilePortfolioDataAccessObject(PORTFOLIOS_CSV_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel,
                userDataAccessObject, portfolioDataAccessObject);
        views.add(signupView, signupView.viewName);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, loggedInViewModel,
                userDataAccessObject, signupView);
        views.add(loginView, loginView.viewName);

        StockPriceDataAccessInterface stockInfoClient = new StockInfoClient();

        LoggedInView loggedInView = LoggedInUseCaseFactory.create(loggedInViewModel, loginViewModel, viewManagerModel,
                userDataAccessObject, deleteViewModel, portfolioDataAccessObject, loginView, new StockFieldValidatorImpl(),
                showViewModel, stockInfoClient);
        views.add(loggedInView, loggedInView.viewName);


        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);

        // todo: Test runner code for StockInfoClient (delete code below later)
//        StockInfoClient client = new StockInfoClient();
//        JSONObject result = client.getStockInfo("AAPL");
//        HashMap<String, HashMap<String, String>> yeah = ShowInteractor.jsonToHashMap(result);
//        System.out.println(yeah);
//        HashMap yeahh = (HashMap) yeah;
//        System.out.println(yeahh);
//        System.out.println(yeahh.getClass().getSimpleName());
//        System.out.println("__________________________________________");
//        System.out.println(yeahh.keySet());
//        HashMap yeahhh = (HashMap) yeahh.get("2020-08-05");
//        System.out.println(yeahhh.getClass().getSimpleName());
//        System.out.println(yeahhh.get("4. close").getClass().getSimpleName());
    }
}