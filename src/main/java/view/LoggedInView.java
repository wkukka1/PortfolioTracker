package view;

import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.removeStock.RemoveStockController;
import interface_adapter.logged_in.add_stock.AddStockController;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeleteState;
import org.apache.commons.lang3.StringUtils;
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
    private final LoginView loginView;
    private final JFrame appFrame;
    private final LogoutController logoutController;

    private final RemoveStockController removeStockController;
    JLabel title;
    JLabel netProfitLabel;
    JLabel netProfitValue;
    JLabel stocksScrollableListLabel;
    JButton addStockButton;
    JButton logOut;
    JButton deleteUser;
    JPanel stocksScrollableList;

    /**
     * A window with a title, a "Net Profit" label, a value for net profit, and an "Add Stock" button.
     */
    public LoggedInView(JFrame appFrame, LoggedInViewModel loggedInViewModel, DeleteState deleteState,
                        DeleteController deleteController, LoginView loginView, StockFieldValidator stockFieldValidator,
                        AddStockController addStockController, LogoutController logoutController, RemoveStockController removeStockController) {
        this.appFrame = appFrame;
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.deleteState = deleteState;
        this.deleteController = deleteController;
        this.loginView = loginView;
        this.logoutController = logoutController;
        this.stockFieldValidator = stockFieldValidator;
        this.addStockController = addStockController;
        this.removeStockController = removeStockController;

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
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0; // Allow title to expand horizontally
        gbc.weighty = 0.0; // Allow title to expand vertically
        this.add(title, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(netProfitLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(netProfitValue, gbc);

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
        this.add(stocksScrollableListLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(stocksScrollableList, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0; // Allow log out button to expand horizontally
        gbc.weighty = 0.0; // Reset weight
        this.add(logOut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(logOut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1.0; // Allow delete user button to expand horizontally
        gbc.weighty = 0.0; // Reset weight
        this.add(deleteUser, gbc);
    }
    //TODO: Implement the Remove Stock button next to the stocks that have been added
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

        }
        if (evt.getSource() == deleteUser) {
            deleteConfirmation();
        } else if (evt.getSource() == logOut) {
            logoutController.execute(loggedInViewModel.getLoggedInUser());
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
        if (!StringUtils.isEmpty(state.getAddStockError())) {
            JOptionPane.showMessageDialog(this, state.getAddStockError());
        }
        if (!StringUtils.isEmpty(state.getUsername()) &&
                StringUtils.isEmpty(loggedInViewModel.getState().getAddStockError())) {
            this.remove(stocksScrollableList);
            stocksScrollableList = new ScrollableStockList(state.getTickersToAggregatedQuantities());

            // Accessing each component to get access to the stock components
            Component root = stocksScrollableList.getComponent(0); // ScrollPane
            Component viewPort = ((JScrollPane) root).getComponent(0); // ViewPort
            Component dataPanelComponent = ((JViewport) viewPort).getComponent(0); // DataPanel
            JPanel dataPanel = (JPanel) dataPanelComponent;

            Component[] stockComponents = dataPanel.getComponents(); // The list of stocks

            for (int i = 0; i < stockComponents.length; i++){
                // Instantiate the Sell button for every stock in the scroll field
                JButton sellStockButton = (JButton) ((JPanel) stockComponents[i]).getComponent(4);

                JLabel tickerLabel = (JLabel) ((JPanel) stockComponents[i]).getComponent(1);
                String tickerSymbol = tickerLabel.getText(); // String ticker symbol of the stock to sell

                // Add an action listener to call removeStock.execute() to sell the stock
                sellStockButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = loggedInViewModel.getLoggedInUser();
                        removeStockController.execute(tickerSymbol, username);
                    }
                });
            }
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 4;
            gbc.anchor = GridBagConstraints.EAST;

            stocksScrollableList.revalidate();
            this.add(stocksScrollableList, gbc);
        }
        appFrame.pack();
    }
}
