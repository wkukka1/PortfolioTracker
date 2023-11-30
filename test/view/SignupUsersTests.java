package view;

import app.Main;
import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import entity.CommonUserFactory;
import entity.Portfolio;
import entity.UserFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertNotNull;

public class SignupUsersTests {

    private String userPath = "./users.csv";
    private String portfolioPath = "./portfolios.csv";

    public void SignUpUser(int i) {
        i -= 1;
        JButton signUpBtn = getSignupBtn();
        LabelTextPanel[] textFields = getSignupTextFields();

        textFields[0].setText("user" + i);
        textFields[1].setText("password" + i);
        textFields[2].setText("password" + i);

        createCloseTimer().start();

        signUpBtn.doClick();
    }

    public LabelTextPanel[] getSignupTextFields() {
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
        SignupView lv = (SignupView) jp2.getComponent(0);

        LabelTextPanel username = (LabelTextPanel) lv.getComponent(1);
        LabelTextPanel password1 = (LabelTextPanel) lv.getComponent(2);
        LabelTextPanel password2 = (LabelTextPanel) lv.getComponent(3);

        // Assuming logIn button is at index 0 in the buttons JPanel
        return new LabelTextPanel[]{username, password1, password2};
    }

    public JButton getSignupBtn() {
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
        SignupView lv = (SignupView) jp2.getComponent(0);

        JPanel buttons = (JPanel) lv.getComponent(4);

        // Assuming logIn button is at index 0 in the buttons JPanel
        return (JButton) buttons.getComponent(0);
    }

    private boolean userExists(String user) {
        try {
            BufferedReader userReader = new BufferedReader(new FileReader(userPath));
            BufferedReader portfoliosReader = new BufferedReader(new FileReader(portfolioPath));

            int index = 0;

            String userLine = userReader.readLine();
            String portfolioLine = portfoliosReader.readLine();
            int userId = -1;
            while (userLine != null && userId == -1) {
                String[] line = userLine.split(",");
                if (Objects.equals(line[index], user)) {
                    userId = Integer.parseInt(line[3]);
                }
                userLine = userReader.readLine();
            }

            if (userId == -1) {
                return false;
            }

            while (portfolioLine != null) {
                String[] line = portfolioLine.split(",");
                if (Objects.equals(line[index], String.valueOf(userId))) {
                    return true;
                }
                portfolioLine = portfoliosReader.readLine();
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    private JButton getSwitchButton(){
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
        LoginView lv = (LoginView) jp2.getComponent(1);

        JPanel buttons = (JPanel) lv.getComponent(5);

        // Assuming logIn button is at index 0 in the buttons JPanel
        return (JButton) buttons.getComponent(1);
    }


    @org.junit.Test
    public void testGetSignUpTextFields() {
        Main.main(null);
        LabelTextPanel[] textFields = getSignupTextFields();
        assert (textFields[0].getName().equals("username") && textFields[1].getName().equals("password1")
                && textFields[2].getName().equals("password2"));
    }

    @org.junit.Test
    public void testGetSignupBtn() {
        Main.main(null);
        JButton button = getSignupBtn();
        System.out.println(button.getText());
        assert (button.getText().equals("Sign up"));
    }

    @org.junit.Test
    public void testGetSwitchButton(){
        Main.main(null);
        JButton button = getSwitchButton();
        System.out.println(button.getText());
        assert (button.getText().equals("Sign Up"));
    }

    @org.junit.Test
    public void testSignupOneUser() {
        Main.main(null);

        JButton signupBtn = getSignupBtn();
        LabelTextPanel[] textPanels = getSignupTextFields();

        JButton switchButton = getSwitchButton();

        switchButton.doClick();

        createCloseTimer().start();

        textPanels[0].setText("user");
        textPanels[1].setText("password");
        textPanels[2].setText("password");

        createCloseTimer().start();

        signupBtn.doClick();

        createCloseTimer().start();

        assert userExists("user");
    }

    private Timer createCloseTimer() {
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Window[] windows = Window.getWindows();
                for (Window window : windows) {

                    if (window instanceof JDialog) {

                        JDialog dialog = (JDialog) window;

                        // this ignores old dialogs
                        if (dialog.isVisible()) {
                            String s = ((JOptionPane) ((BorderLayout) dialog.getRootPane()
                                    .getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER)).getMessage().toString();
                            System.out.println("message = " + s);

                            // store the information we got from the JDialog
                            DeleteUsersTest.message = s;
                            DeleteUsersTest.popUpDiscovered = true;

                            System.out.println("disposing of..." + window.getClass());
                            window.dispose();
                        }
                    }
                }
            }

        };
        Timer t = new Timer(1000, close);
        t.setRepeats(false);
        return t;
    }
}