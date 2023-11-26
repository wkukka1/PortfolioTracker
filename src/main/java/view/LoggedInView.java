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

        title = new JLabel("Home");
        JPanel netProfitPanel = new JPanel();
        netProfitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        netProfitLabel = new JLabel("Net Profit:");
        netProfitValue = new JLabel(); // You can set the value later

        netProfitPanel.add(netProfitLabel);
        netProfitPanel.add(netProfitValue);

        addStockButton = new JButton("Add Stock");
        addStockButton.addActionListener(this);

        logOut = new JButton(LoggedInViewModel.LOGOUT_BUTTON_LABEL);
        logOut.addActionListener(this);
        deleteUser = new JButton(LoggedInViewModel.DELETE_USER_LABEL);
        deleteUser.addActionListener(this);

        this.setLayout(new GridBagLayout()); // Use GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        title = new JLabel("Home");
        netProfitLabel = new JLabel("Net Profit:");
        netProfitValue = new JLabel(); // You can set the value later

        addStockButton = new JButton("Add Stock");
        addStockButton.addActionListener(this);

        logOut = new JButton(LoggedInViewModel.LOGOUT_BUTTON_LABEL);
        logOut.addActionListener(this);

        deleteUser = new JButton(LoggedInViewModel.DELETE_USER_LABEL);
        deleteUser.addActionListener(this);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0; // Allow title to expand horizontally
        gbc.weighty = 0.0; // Allow title to expand vertically
        this.add(title, gbc);

        // Net Profit Label and Value
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Anchor to the top-left
        gbc.weightx = 1.0; // Allow net profit panel to expand horizontally
        gbc.weighty = 0.0; // Fixed height
        this.add(netProfitPanel, gbc);

        // Add Stock Button
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.weightx = 0.0; // Reset weight
        gbc.weighty = 1.0; // Reset weight
        this.add(addStockButton, gbc);

        // Log Out Button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0; // Allow log out button to expand horizontally
        gbc.weighty = 0.0; // Reset weight
        this.add(logOut, gbc);

        // Delete User Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0; // Allow delete user button to expand horizontally
        gbc.weighty = 0.0; // Reset weight
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
