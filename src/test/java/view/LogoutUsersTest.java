package view;

import app.Main;
import com.fasterxml.jackson.core.JsonProcessingException;
import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import entity.CommonUserFactory;
import entity.Portfolio;
import entity.UserFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class LogoutUsersTest {
    private void loginUser(String username, String password) {
        LabelTextPanel[] textfield = getTextfeilds();
        LabelTextPanel usernameInputField = textfield[0];
        LabelTextPanel passwordInputField = textfield[1];

        usernameInputField.setText(username);
        passwordInputField.setText(password);

        getLoginBtn().doClick();

        getloggedInUser();
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
        JButton button =  (JButton) buttons.getComponent(0);

        return button;
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

    private String getloggedInUser() {
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
        // Assuming LoggedInView is at index 1 in the JPanel
        LoggedInView lv = (LoggedInView) jp2.getComponent(2);

        return lv.loggedInViewModel.getLoggedInUser();
    }

    private JButton getLogoutBtn() {
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
        // Assuming LoggedInView is at index 1 in the JPanel
        LoggedInView lv = (LoggedInView) jp2.getComponent(2);

        return (JButton) lv.getComponent(4);
    }

    private JButton getLogoutBtn1() {
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
        // Assuming LoggedInView is at index 1 in the JPanel
        LoggedInView lv = (LoggedInView) jp2.getComponent(2);

        return (JButton) lv.getComponent(4);
    }

    private boolean logoutUser() throws JsonProcessingException {
        addUser(1);
        Main.main(null);
        loginUser("user0", "password0");
        getLogoutBtn().doClick();
        return getloggedInUser() == null;
    }

    private boolean popUpExist() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame && ((JFrame) window).getTitle().equals("Logout Error")) {
                return true;
            }
        }
       return false;
    }

    @org.junit.Test
    public void testLogoutOneUser() throws JsonProcessingException {
        assert logoutUser();
    }

    @org.junit.Test
    public void testLogoutError() throws JsonProcessingException {
        Main.main(null);
        getLogoutBtn1().doClick();
        assert popUpExist();
    }

}
