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
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeleteUsersTest {
    static String message = "";
    static boolean popUpDiscovered = false;

    public void SignUpUser(int i){
        i -= 1;
        JButton signUpBtn = getSignupBtn();
        LabelTextPanel[] textFields = getSignupTextFields();

        textFields[0].setText("user"+i);
        textFields[1].setText("password"+i);
        textFields[2].setText("password"+i);

        createCloseTimer().start();

        signUpBtn.doClick();
    }

    public LabelTextPanel[] getSignupTextFields(){
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

        System.out.println(username.getText());
        System.out.println(password1.getText());
        System.out.println(password2.getText());

        // Assuming logIn button is at index 0 in the buttons JPanel
        return new LabelTextPanel[] {username, password1, password2};
    }


    public JButton getSignupBtn(){
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

    public JButton getLoginBtn() {
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
        return (JButton) buttons.getComponent(0);
    }


    public LabelTextPanel[] getTextFields() {
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

        LabelTextPanel usernameTextField = (LabelTextPanel) lv.getComponent(1);
        LabelTextPanel passwordTextField = (LabelTextPanel) lv.getComponent(3);
        // Assuming logIn button is at index 0 in the buttons
        return new LabelTextPanel[]{usernameTextField, passwordTextField};
    }

    public JButton getDeleteBtn() {
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

        return (JButton) lv.getComponent(5);
    }


    public boolean deleteAccount(int num) {
        num = num - 1;

        // Get all the buttons
        LabelTextPanel[] textFields = getTextFields();

        int initialUsers;
        int initialPortfolios;
        int finalUsers;
        int finalPortfolios;

        try {
            initialUsers = countLines("./users.csv");
            initialPortfolios = countLines("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        createCloseTimer().start();

        textFields[0].setText("user" + num);
        textFields[1].setText("password" + num);

        createCloseTimer().start();

        // Create a SwingWorker to perform the login operation in the background
        getLoginBtn().doClick();

        // Start the close timer before clicking the delete button
        createCloseTimer().start();

        JButton deleteBtn = getDeleteBtn();
        deleteBtn.doClick();

        try {
            finalUsers = countLines("./users.csv");
            finalPortfolios = countLines("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return initialUsers == finalUsers + 1 && initialPortfolios == finalPortfolios + 1;
    }

    @org.junit.Test
    public void testGetSignupBtn(){
        Main.main(null);
        JButton button = getSignupBtn();
        System.out.println(button.getText());
        assert (button.getText().equals("Sign up"));
    }

    @org.junit.Test
    public void testGetLoginBtn() {
        Main.main(null);
        JButton button = getLoginBtn();
        System.out.println(button.getText());
        assert (button.getText().equals("Log in"));
    }

    @org.junit.Test
    public void testGetSignUpTextFields(){
        Main.main(null);
        LabelTextPanel[] textFields = getSignupTextFields();
        assert (textFields[0].getText().equals("username") && textFields[1].getText().equals("password1")
                && textFields[2].getText().equals("password2"));
    }

    @org.junit.Test
    public void testGetTextFeilds() {
        Main.main(null);
        LabelTextPanel[] textField = getTextFields();
        System.out.println(textField[0].getLabel().getText());
        assert (textField[0].getLabel().getText().equals("Username") &&
                textField[1].getLabel().getText().equals("Password"));
    }

    @org.junit.Test
    public void testGetDeleteBtn() {
        Main.main(null);
        JButton button = getDeleteBtn();
        System.out.println(button.getText());
        assert (button.getText().equals("Delete Account"));
    }

    @org.junit.Test
    public void testDeleteOneAccount() {
        Main.main(null);
        SignUpUser(1);
        assert deleteAccount(1);

    }

    @org.junit.Test
    public void testDeleteMultipleAccounts() {
        Main.main(null);
        addUser(5);
        boolean d1 = deleteAccount(1);
        assert d1;
        boolean d2 = deleteAccount(2);
        assert d2;
        boolean d3 = deleteAccount(3);
        assert d3;
        assert deleteAccount(4);
        assert deleteAccount(5);
        assert true;
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

    private static int countLines(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        int lineCount = 0;
        while (reader.readLine() != null) {
            lineCount++;
        }
        return lineCount;
    }


}

