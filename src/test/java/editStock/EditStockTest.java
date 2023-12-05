package editStock;

import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import entity.*;
import interface_adapter.logged_in.LoggedInViewModel;
import use_case.editStock.*;
import view.LabelTextPanel;
import view.LoggedInView;
import view.LoginView;
import app.Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EditStockTest {

    public JButton getEditButton(){
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }
        assertNotNull(app);

        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);
        // Assuming LoginView is at index 1 in the JPanel
        LoggedInView lv = (LoggedInView) jp2.getComponent(2);
        //Assuming the Delete User button is in index 5, then Edit Stock button should be next, at index 6
        return (JButton) lv.getComponent(7);
    }

    private JButton getLoginBtn() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame && ((JFrame) window).getTitle().equals("PortfolioTracker")) {
                app = (JFrame) window;
            }
        }
        assertNotNull(app);

        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);
        // Assuming LoginView is at index 1 in the JPanel
        LoginView lv = (LoginView) jp2.getComponent(1);

        JPanel buttons = (JPanel) lv.getComponent(5);

        // Assuming logIn button is at index 0 in the buttons JPanel
        return (JButton) buttons.getComponent(0);
    }

    private LabelTextPanel[] getTextfeilds() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame && ((JFrame) window).getTitle().equals("PortfolioTracker")) {
                app = (JFrame) window;
            }
        }
        assertNotNull(app);

        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);
        // Assuming LoginView is at index 1 in the JPanel
        LoginView lv = (LoginView) jp2.getComponent(1);

        LabelTextPanel usernameTextField = (LabelTextPanel) lv.getComponent(1);
        LabelTextPanel passwordTextField = (LabelTextPanel) lv.getComponent(3);
        // Assuming logIn button is at index 0 in the buttons
        return new LabelTextPanel[]{usernameTextField, passwordTextField};
    }

    private Object[] getEditStockTextfields() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }
        assertNotNull(app);

        Component root = app.getComponent(0);
        Container cp = ((JRootPane) root).getContentPane();

        LabelTextPanel tickerTextField = (LabelTextPanel) cp.getComponent(0);
        LabelTextPanel sharesTextField = (LabelTextPanel) cp.getComponent(1);

        JPanel okButtonPanel = (JPanel) cp.getComponent(2);
        JButton okButton = findButtonInPanel(okButtonPanel);
        // Assuming logIn button is at index 0 in the buttons
        return new Object[] {tickerTextField, sharesTextField, okButton};
    }
    private JButton findButtonInPanel(JPanel panel) {
        // Search for the JButton within the panel
        for (Component component : panel.getComponents()) {
            if (component instanceof JButton) {
                return (JButton) component;
            }
        }
        throw new RuntimeException("JButton not found in the specified JPanel.");
    }

    private boolean popUpExist() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                return true;
            }
        }
        return false;
    }

    @org.junit.Test
    public void editOneStockTest(){
        // LoggedInViewModel
        LoggedInViewModel viewModel = new LoggedInViewModel();

        // Portfolio DAO
        UserFactory uf = new CommonUserFactory();
        FileUserDataAccessObject fudao;
        FilePortfolioDataAccessObject fpdao;
        try {
            fudao = new FileUserDataAccessObject("./users.csv", uf);
            fpdao = new FilePortfolioDataAccessObject("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Stock> stockList = new ArrayList<>();

        Stock stock = new Stock("AAPL", LocalDateTime.now(), 1, 10);

        stockList.add(stock);
        fudao.save(uf.create("user", "password", LocalDateTime.now(), 1));
        fpdao.savePortfolio(new Portfolio(stockList, 10, 1));


        Main.main(null);

        LabelTextPanel[] textfield = getTextfeilds();
        LabelTextPanel usernameInputField = textfield[0];
        LabelTextPanel passwordInputField = textfield[1];


        usernameInputField.setText("user");
        passwordInputField.setText("password");

        getLoginBtn().doClick();

        getEditButton().doClick();


        Object[] editTextfield = getEditStockTextfields();
        LabelTextPanel tickerInputField = (LabelTextPanel) editTextfield[0];
        LabelTextPanel qtyInputField = (LabelTextPanel) editTextfield[1];
        JButton conf = (JButton) editTextfield[2];

        tickerInputField.setText("AAPL");
        qtyInputField.setText("10");

        conf.doClick();
    }

    @org.junit.Test
    public void multipleStocksTest(){
        // LoggedInViewModel
        LoggedInViewModel viewModel = new LoggedInViewModel();

        // Portfolio DAO
        UserFactory uf = new CommonUserFactory();
        FileUserDataAccessObject fudao;
        FilePortfolioDataAccessObject fpdao;
        try {
            fudao = new FileUserDataAccessObject("./users.csv", uf);
            fpdao = new FilePortfolioDataAccessObject("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Stock> stockList = new ArrayList<>();

        Stock stock1 = new Stock("AAPL", LocalDateTime.now(), 1, 10);
        Stock stock2 = new Stock("GOOGL", LocalDateTime.now(), 5, 5);

        stockList.add(stock1);

        stockList.add(stock2);
        fudao.save(uf.create("user", "password", LocalDateTime.now(), 1));
        fpdao.savePortfolio(new Portfolio(stockList, 10, 1));


        Main.main(null);

        LabelTextPanel[] textfield = getTextfeilds();
        LabelTextPanel usernameInputField = textfield[0];
        LabelTextPanel passwordInputField = textfield[1];


        usernameInputField.setText("user");
        passwordInputField.setText("password");

        getLoginBtn().doClick();

        getEditButton().doClick();


        Object[] editTextfield = getEditStockTextfields();
        LabelTextPanel tickerInputField = (LabelTextPanel) editTextfield[0];
        LabelTextPanel qtyInputField = (LabelTextPanel) editTextfield[1];
        JButton conf = (JButton) editTextfield[2];

        tickerInputField.setText("AAPL");
        qtyInputField.setText("10");

        conf.doClick();
    }

    @org.junit.Test
    public void failViewTest(){
        // LoggedInViewModel
        LoggedInViewModel viewModel = new LoggedInViewModel();

        // Portfolio DAO
        UserFactory uf = new CommonUserFactory();
        FileUserDataAccessObject fudao;
        FilePortfolioDataAccessObject fpdao;
        try {
            fudao = new FileUserDataAccessObject("./users.csv", uf);
            fpdao = new FilePortfolioDataAccessObject("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Stock> stockList = new ArrayList<>();

        Stock stock = new Stock("AAPL", LocalDateTime.now(), 10, 10);

        stockList.add(stock);
        fudao.save(uf.create("user", "password", LocalDateTime.now(), 1));
        fpdao.savePortfolio(new Portfolio(stockList, 10, 1));


        Main.main(null);

        LabelTextPanel[] textfield = getTextfeilds();
        LabelTextPanel usernameInputField = textfield[0];
        LabelTextPanel passwordInputField = textfield[1];


        usernameInputField.setText("user");
        passwordInputField.setText("password");

        getLoginBtn().doClick();

        getEditButton().doClick();


        Object[] editTextfield = getEditStockTextfields();
        LabelTextPanel tickerInputField = (LabelTextPanel) editTextfield[0];
        LabelTextPanel qtyInputField = (LabelTextPanel) editTextfield[1];
        JButton conf = (JButton) editTextfield[2];

        tickerInputField.setText("GOOGL");
        qtyInputField.setText("10");

        conf.doClick();
        assert popUpExist();
    }

}
