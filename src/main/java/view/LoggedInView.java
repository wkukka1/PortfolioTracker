package view;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.show.ShowController;
import interface_adapter.show.ShowState;
import interface_adapter.show.ShowViewModel;
import view.validation.StockFieldValidator;

import javax.swing.*;
import javax.validation.ValidationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    private final StockFieldValidator stockFieldValidator;
    public final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private DeleteState deleteState;
    private ShowState showState;
    private final DeleteController deleteController;
    private final ShowController showController;
    private final LoginView loginView;

    JLabel title;
    JLabel netProfitLabel;
    JLabel netProfitValue;
    JButton addStockButton;
    JButton logOut;
    JButton deleteUser;
    JButton showButton;

    /**
     * A window with a title, a "Net Profit" label, a value for net profit, and an "Add Stock" button.
     */
    public LoggedInView(LoggedInViewModel loggedInViewModel, DeleteState deleteState, DeleteController deleteController,
                        LoginView loginView, StockFieldValidator stockFieldValidator, ShowState showState, ShowController showController) {
        this.loggedInViewModel = loggedInViewModel;
        this.showState = showState;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.deleteState = deleteState;
        this.deleteController = deleteController;
        this.showController = showController;
        this.loginView = loginView;
        this.stockFieldValidator = stockFieldValidator;


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

        showButton = new JButton("Update Plot and Net Profit");
        showButton.addActionListener(this);

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

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(showButton, gbc);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == addStockButton) {
            // Handle the "Add Stock" button click
            JTextField tickerField = new JTextField(10);
            JTextField amountField = new JTextField(10);

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            JFormattedTextField dateField = new JFormattedTextField(df);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Enter Ticker Symbol:"));
            panel.add(tickerField);
            panel.add(new JLabel("Enter Date (dd/mm/yyyy):"));
            panel.add(dateField);
            panel.add(new JLabel("Enter Amount (USD):"));
            panel.add(amountField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Add Stock",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String ticker = tickerField.getText();
                String date = dateField.getText();
                String amountStr = amountField.getText();

                try {
                    validateAllFieldsOrShowErrorMsg(ticker, date, amountStr);
                } catch (ValidationException validationException) {
                    System.out.println("Stock Field Validation Exception Occurred");;
                }
            }

        } if (evt.getSource() == deleteUser){
            deleteController.execute(loggedInViewModel.getLoggedInUser());
//            deleteState = deleteState.getState();
        } if (evt.getSource() == logOut) {
            System.out.println("Click " + evt.getActionCommand()); // Handle logout button
        } if (evt.getSource() == showButton) {
            showController.execute(loggedInViewModel.getState().getUserID());
        }
    }

    private void validateAllFieldsOrShowErrorMsg(String ticker, String date, String amountStr)
            throws ValidationException {
        if (!ticker.isEmpty() && !date.isEmpty() && !amountStr.isEmpty()) {
            if (!this.stockFieldValidator.isDateStrValid(date)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid date.");
            } else if (!this.stockFieldValidator.isTickerStrValid(ticker)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid ticker symbol.");
            } else if (!this.stockFieldValidator.isAmountStrValid(amountStr)) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid (and non-zero) amount.");
            } else {
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }

        throw new ValidationException();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        // Update the net profit value using state data
        netProfitValue.setText(state.getNetProfit());
    }
}
