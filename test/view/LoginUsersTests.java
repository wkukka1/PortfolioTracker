package view;

import app.Main;
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

    private JButton getloginBtn(){
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

    private LabelTextPanel[] getTextfeilds(){
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


    private boolean loginUser(String username, String password) {
        LabelTextPanel[] textfield = getTextfeilds();
        LabelTextPanel usernameInputField = textfield[0];
        LabelTextPanel passwordInputField = textfield[1];

        usernameInputField.setText(username);
        passwordInputField.setText(password);

        getloginBtn().doClick();

        boolean result = getloggedInUser().equals(username);

        System.out.println("test");
        return result;
    }

    private String getloggedInUser() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window:windows){
            if(window instanceof JFrame && ((JFrame) window).getTitle().equals("PortfolioTracker")){
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


    @org.junit.Test
    public void loginOneUserTest() throws IOException {
        addUser(1);
        Main.main(null);
        assert loginUser("user0", "password0");
    }

    @org.junit.Test
    public void loginMultipleUsersTest() throws IOException {
        addUser(5);
        Main.main(null);
        assert loginUser("user0", "password0");
        assert loginUser("user1", "password1");
        assert loginUser("user2", "password2");
        assert loginUser("user3", "password3");
        assert loginUser("user4", "password4");
    }
}
