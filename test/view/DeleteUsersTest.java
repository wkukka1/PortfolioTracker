package view;

import app.Main;
import app.SignupUseCaseFactory;
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

    public LabelTextPanel[] getTextFeilds() {
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

    public boolean deleteAccount(int num) {
        addUser(num);
        JButton loginBtn = getLoginBtn();
        int initalUsers;
        int intialPortfolios;
        int finalUsers;
        int finalPortfolios;
        createCloseTimer().start();

        loginBtn.doClick();
        try {
            initalUsers = countLines("./users.csv");
            intialPortfolios = countLines("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            finalUsers = countLines("./users.csv");
            finalPortfolios = countLines("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return initalUsers == finalUsers + num && intialPortfolios == finalPortfolios + num;
    }


    @org.junit.Test
    public void testGetLoginBtn() {
        Main.main(null);
        JButton button = getLoginBtn();
        System.out.println(button.getText());
        assert (button.getText().equals("Log in"));
    }

    @org.junit.Test
    public void testGetTextFeilds() {
        Main.main(null);
        LabelTextPanel[] textField = getTextFeilds();
        System.out.println(textField[0].getLabel().getText());
        assert (textField[0].getLabel().getText().equals("Username") &&
                textField[1].getLabel().getText().equals("Password"));
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

