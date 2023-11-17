package view;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;

    JLabel title;
    JLabel netProfitLabel;
    JLabel netProfitValue;
    JButton addStockButton;
    JButton logOut;
    JButton deleteUser;

    /**
     * A window with a title, a "Net Profit" label, a value for net profit, and an "Add Stock" button.
     */
    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

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
    /*
    Creates a pop that the user needs to click to continue deleting their account
     */
    private void showDeleteAccountConfirmation() {
        // Create a JOptionPane with a YES_NO_OPTION dialog type
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete your account?",
                "Delete Account Confirmation",
                JOptionPane.YES_NO_OPTION);

        // Check the user's choice
        if (choice == JOptionPane.YES_OPTION) {
            // User clicked YES
            System.out.println("Account deleted");
            System.out.print(loggedInViewModel.getLoggedInUser());
            //TODO ADD logic for handling the cancellation of account deletion here
        } else {
            // User clicked NO or closed the dialog
            System.out.println("Account not deleted");
            //TODO Send the user back to the loggedIn screen
        }
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

        }if (evt.getSource() == deleteUser){
            showDeleteAccountConfirmation();
        }else if (evt.getSource() == logOut) {
            System.out.println("Click " + evt.getActionCommand()); // Handle logout button
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        // Update the net profit value using state data
        netProfitValue.setText(state.getNetProfit());
    }
}
