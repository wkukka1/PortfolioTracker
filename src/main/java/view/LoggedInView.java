package view;

import com.fasterxml.jackson.core.JsonProcessingException;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logged_in.add_stock.AddStockController;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.editStock.EditStockController;
import interface_adapter.logged_in.show.ShowController;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartPanel;
import view.components.ScrollableStockList;
import interface_adapter.logout_user.LogoutController;
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
import java.util.HashMap;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {

    private final StockFieldValidator stockFieldValidator;
    private final AddStockController addStockController;
    public final String viewName = "logged in";
    public final LoggedInViewModel loggedInViewModel;
    private DeleteState deleteState;
    private final DeleteController deleteController;
    private final ShowController showController;
    private final LoginView loginView;
    private final EditStockController editStockController;

    private final JFrame appFrame;
    private final LogoutController logoutController;
    JLabel title;
    JLabel netProfitLabel;
    JLabel netProfitValue;
    JLabel stocksScrollableListLabel;
    JButton addStockButton;
    JButton logOut;
    JButton deleteUser;

    JButton editStock;
    JButton showButton;
    JPanel stocksScrollableList;
    JPanel plot;

    /**
     * A window with a title, a "Net Profit" label, a value for net profit, and an "Add Stock" button.
     */

    public LoggedInView(JFrame appFrame, LoggedInViewModel loggedInViewModel, DeleteState deleteState,
                        DeleteController deleteController, LoginView loginView, StockFieldValidator stockFieldValidator,
                        AddStockController addStockController, LogoutController logoutController, ShowController showController, EditStockController editStockController) {
        this.appFrame = appFrame;
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.deleteState = deleteState;
        this.deleteController = deleteController;
        this.showController = showController;
        this.loginView = loginView;
        this.logoutController = logoutController;
        this.stockFieldValidator = stockFieldValidator;
        this.editStockController = editStockController;
        this.addStockController = addStockController;

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

        editStock = new JButton(LoggedInViewModel.EDIT_STOCK_LABEL);
        editStock.addActionListener(this);

        showButton = new JButton("Update Plot and Net Profit");
        showButton.addActionListener(this);

        this.setLayout(new GridBagLayout()); // Use GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        title = new JLabel("Home");
        netProfitLabel = new JLabel("Net Profit (USD):");
        netProfitValue = new JLabel(); // You can set the value later

        addStockButton = new JButton("Add Stock");
        addStockButton.addActionListener(this);

        logOut = new JButton(LoggedInViewModel.LOGOUT_BUTTON_LABEL);
        logOut.addActionListener(this);

        deleteUser = new JButton(LoggedInViewModel.DELETE_USER_LABEL);
        deleteUser.addActionListener(this);

        stocksScrollableList = new ScrollableStockList(new HashMap<>());
        stocksScrollableListLabel = new JLabel("Currently Held Assets:");

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0; // Allow title to expand horizontally
        gbc.weighty = 0.0; // Allow title to expand vertically
        this.add(title, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(netProfitLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(netProfitValue, gbc);

        // Add Stock Button
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 0.0; // Reset weight
        gbc.weighty = 1.0; // Reset weight
        this.add(addStockButton, gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(logOut, gbc);

        gbc.gridx = 5;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(deleteUser, gbc);

        gbc.gridx = 6;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(showButton, gbc);

        // Sets the coordinates for the Edit Stock button
        gbc.gridx = 7;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        // Creates the Edit Stock button
        this.add(editStock, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        plot = new JPanel();
        ChartPanel chart = loggedInViewModel.getState().getPanel();
        if (chart != null) {
            plot.add(chart);
        }
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.gridwidth = 6;
        gbc.gridheight = 2;
        this.add(plot, gbc);
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.gridheight = 5;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(stocksScrollableList, gbc);

//        gbc.gridx = 0;
//        gbc.gridy = 4;
//        gbc.gridwidth = 4;
//        gbc.anchor = GridBagConstraints.EAST;
//        gbc.weightx = 1.0; // Allow delete user button to expand horizontally
//        gbc.weighty = 0.0; // Reset weight
//        this.add(deleteUser, gbc);
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
                    return;
                }

                addStockController.execute(ticker, date, amountStr, loggedInViewModel.getState().getUserID());
            }


        } if (evt.getSource() == deleteUser){
            deleteConfirmation();
        } if (evt.getSource() == logOut) {
            logoutController.execute(loggedInViewModel.getLoggedInUser());
        } if (evt.getSource() == editStock) {
            LabelTextPanel tickerField = new LabelTextPanel(new JLabel("Enter Ticker Symbol:"), new JTextField(10));
            LabelTextPanel amountField = new LabelTextPanel(new JLabel("Enter Amount of Shares:"), new JTextField(10));

            JFrame editStockFrame = new JFrame("Edit Stock");
            editStockFrame.setLayout(new BoxLayout(editStockFrame.getContentPane(), BoxLayout.Y_AXIS));

            // Adding the text fields
            editStockFrame.add(tickerField);
            editStockFrame.add(amountField);

            // Creating a panel for the buttons with a FlowLayout
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String ticker = tickerField.getText();
                    double newQuantity = Double.parseDouble(amountField.getText());
                    String username = loggedInViewModel.getLoggedInUser();

                    editStockController.execute(ticker, newQuantity, username);
                    // TODO: Call ShowController.execute() to update the portfolio's netWorth

                    editStockFrame.dispose();  // Close the frame after the operation
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editStockFrame.dispose();  // Close the frame on cancel
                }
            });

            // Adding buttons to the button panel
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);

            // Adding the button panel to the main frame
            editStockFrame.add(buttonPanel);

            editStockFrame.setSize(300, 200);
            editStockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editStockFrame.setLocationRelativeTo(this);
            editStockFrame.setVisible(true);
        } if (evt.getSource() == showButton) {
            try {
                showController.execute(loggedInViewModel.getState().getUserID());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void deleteConfirmation(){
        JFrame popup = new JFrame("Confirmation");
        popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel text = new JLabel("Are you sure you want to delete your account?");

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteController.execute(loggedInViewModel.getLoggedInUser());
                popup.dispose();
            }
        });

        JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup.dispose();
            }
        });

        // Set layout manager to null for manual positioning
        popup.setLayout(null);

        // Set position and size of the label
        text.setBounds(50, 20, 300, 30); // Adjust the values as needed

        // Set position and size of the buttons
        yesButton.setBounds(100, 70, 80, 30); // Adjust the values as needed
        noButton.setBounds(200, 70, 80, 30); // Adjust the values as needed

        // Add components to the frame
        popup.add(text);
        popup.add(yesButton);
        popup.add(noButton);

        // Set frame properties
        popup.setSize(400, 150);
        popup.setLocationRelativeTo(this);
        popup.setVisible(true);
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
        netProfitValue.setText("$" + state.getNetProfit());
        GridBagConstraints gbc = new GridBagConstraints();
        if (!StringUtils.isEmpty(state.getAddStockError())) {
            JOptionPane.showMessageDialog(this, state.getAddStockError());
        }
        if (!StringUtils.isEmpty(state.getUsername()) &&
                StringUtils.isEmpty(loggedInViewModel.getState().getAddStockError())) {
            this.remove(stocksScrollableList);
            stocksScrollableList = new ScrollableStockList(state.getTickersToAggregatedQuantities());

            gbc.gridx = 6;
            gbc.gridy = 2;
            gbc.gridheight = 5;
            gbc.anchor = GridBagConstraints.NORTH;

            stocksScrollableList.revalidate();
            this.add(stocksScrollableList, gbc);
        }
        this.remove(plot);
        gbc.gridx = 0;
        gbc.gridy = 1;
        plot = new JPanel();
        ChartPanel chart = loggedInViewModel.getState().getPanel();
        if (chart != null) {
            plot.add(chart);
        }
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.gridwidth = 6;
        gbc.gridheight = 2;
        this.add(plot, gbc);

        appFrame.pack();
    }
}
