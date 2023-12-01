package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sign up";
    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final SignupController signupController;

    private final JButton signUp;
    private final JButton cancel;


    public SignupView(SignupController controller, SignupViewModel signupViewModel) {

        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);

        usernameInfo.setName("username");
        passwordInfo.setName("password1");
        repeatPasswordInfo.setName("password2");

        JPanel buttons = new JPanel();
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);


        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();

                            String username = currentState.getUsername();
                            String password = currentState.getPassword();
                            String repeatPassword = currentState.getRepeatPassword();

                            //Checks if the username, password, repeatpassword is not ""
                            if(!Objects.equals(username, "") && !Objects.equals(password, "") && !Objects.equals(repeatPassword, "")) {
                                signupController.execute(
                                        username,
                                        password,
                                        repeatPassword
                                );
                            }else{
                                JOptionPane.showMessageDialog(SignupView.this,
                                        "Please enter a valid username and password",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            usernameInputField.setText("");
                            passwordInputField.setText("");
                            repeatPasswordInfo.setText("");

                        }
                    }
                }
        );

        SignupView signupView = this;

        cancel.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getSource().equals(cancel)){
                            // Clears the input fields
                            usernameInputField.setText("");
                            repeatPasswordInputField.setText("");
                            passwordInputField.setText("");

                            // Changes the view to the LoginView
                            signupController.setUpLoginView();
                        }
                    }
                }
        );

        // This makes a new KeyListener implementing class, instantiates it, and
        // makes it listen to keystrokes in the usernameInputField.
        //
        // Notice how it has access to instance variables in the enclosing class!
        usernameInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        String text = usernameInputField.getText() + e.getKeyChar();
                        currentState.setUsername(text);
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        passwordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        repeatPasswordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setRepeatPassword(repeatPasswordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        usernameInfo.addPropertyChangeListener(evt -> {
            if("text".equals(evt.getPropertyName())){
                SignupState currentState = signupViewModel.getState();
                currentState.setUsername(evt.getNewValue().toString());
                signupViewModel.setState(currentState);
            }
        });
        passwordInfo.addPropertyChangeListener(evt -> {
            if("text".equals(evt.getPropertyName())){
                SignupState currentState = signupViewModel.getState();
                currentState.setPassword(evt.getNewValue().toString());
                signupViewModel.setState(currentState);
            }
        });

        repeatPasswordInfo.addPropertyChangeListener(evt -> {
            if("text".equals(evt.getPropertyName())){
                SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword((evt.getNewValue().toString()));
                signupViewModel.setState(currentState);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("test");
    }
}