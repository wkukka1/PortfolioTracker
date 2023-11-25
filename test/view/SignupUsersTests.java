package view;

import app.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.Assert.assertNotNull;

public class SignupUsersTests {
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