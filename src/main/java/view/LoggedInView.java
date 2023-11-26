package view;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.logout_user.LogoutController;
import use_case.delete_user.DeleteOutputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private DeleteState deleteState;
    private final DeleteController deleteController;
    private final LoginView loginView;
    private final LogoutController logoutController;
    JLabel title;
    JLabel netProfitLabel;
    JLabel netProfitValue;
    JButton addStockButton;
    JButton logOut;
    JButton deleteUser;

    /**
     * A window with a title, a "Net Profit" label, a value for net profit, and an "Add Stock" button.
     */
    public LoggedInView(LoggedInViewModel loggedInViewModel, DeleteState deleteState, DeleteController deleteController,
                        LoginView loginView, LogoutController logoutController) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.deleteState = deleteState;
        this.deleteController = deleteController;
        this.loginView = loginView;
        this.logoutController = logoutController;
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        title = new JLabel("Home");
        netProfitLabel = new JLabel("Net Profit:");
        netProfitValue = new JLabel(); // You can set the value later

        addStockButton = new JButton("Add Stock");
        addStockButton.addActionListener(this);

        logOut = new JButton(LoggedInViewModel.LOGOUT_BUTTON_LABEL);
        logOut.addActionListener(this);
        deleteUser = new JButton(LoggedInViewModel.DELETE_USER_LABEL);
        deleteUser.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        this.add(title, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        this.add(netProfitLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;

        this.add(netProfitValue, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.EAST;

        this.add(addStockButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;

        this.add(logOut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(deleteUser, gbc);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == addStockButton) {
            // Handle the "Add Stock" button click
            JTextField tickerField = new JTextField(10);
            JTextField dateField = new JTextField(10);
            JTextField amountField = new JTextField(10);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Enter Ticker Symbol:"));
            panel.add(tickerField);
            panel.add(new JLabel("Enter Date:"));
            panel.add(dateField);
            panel.add(new JLabel("Enter Amount:"));
            panel.add(amountField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Add Stock",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String ticker = tickerField.getText();
                String date = dateField.getText();
                String amountStr = amountField.getText();

                if (!ticker.isEmpty() && !date.isEmpty() && !amountStr.isEmpty()) {
                    try {
                        int amount = Integer.parseInt(amountStr);
                        // TODO: SEND THIS TO THE API
                        System.out.println("Add Stock: Ticker - " + ticker + ", Date - " + date + ", Amount - " + amount);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                }
            }

        }
        if (evt.getSource() == deleteUser) {
            deleteController.execute(loggedInViewModel.getLoggedInUser());
//            deleteState = deleteState.getState();
        } else if (evt.getSource() == logOut) {
            logoutController.execute(loggedInViewModel.getLoggedInUser());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        // Update the net profit value using state data
        netProfitValue.setText(state.getNetProfit());
    }
}
