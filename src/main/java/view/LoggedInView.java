package view;

import com.fasterxml.jackson.core.JsonProcessingException;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.removeStock.RemoveStockController;
import interface_adapter.logged_in.add_stock.AddStockController;
import interface_adapter.delete_user.DeleteController;
import interface_adapter.delete_user.DeleteState;
import interface_adapter.editStock.EditStockController;
import interface_adapter.logged_in.currency_conversion.CurrencyController;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

    private final RemoveStockController removeStockController;
    private final CurrencyController currencyController;
    JLabel title;
    JLabel netProfitLabel;
    JLabel netProfitValue;
    JLabel stocksScrollableListLabel;
    JButton addStockButton;
    JButton logOut;
    JButton deleteUser;

    JButton editStock;
    JButton showButton;
    JButton currencyButton;
    JPanel stocksScrollableList;
    JPanel plot;

    /**
     * A window with a title, a "Net Profit" label, a value for net profit, and an "Add Stock" button.
     */

    public LoggedInView(JFrame appFrame, LoggedInViewModel loggedInViewModel, DeleteState deleteState,
                        DeleteController deleteController, LoginView loginView, StockFieldValidator stockFieldValidator,
                        AddStockController addStockController, LogoutController logoutController, ShowController showController,
                        EditStockController editStockController, CurrencyController currencyController, RemoveStockController removeStockController) {
        this.appFrame = appFrame;
        this.loggedInViewModel = loggedInViewModel;
        this.currencyController = currencyController;
        this.loggedInViewModel.addPropertyChangeListener(this);
        this.deleteState = deleteState;
        this.deleteController = deleteController;
        this.showController = showController;
        this.loginView = loginView;
        this.logoutController = logoutController;
        this.stockFieldValidator = stockFieldValidator;
        this.editStockController = editStockController;
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

        editStock = new JButton(LoggedInViewModel.EDIT_STOCK_LABEL);
        editStock.addActionListener(this);

        showButton = new JButton("Update Plot and Net Profit");
        showButton.addActionListener(this);

        currencyButton = new JButton("Change Net Profit Currency");
        currencyButton.addActionListener(this);

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
        gbc.anchor = GridBagConstraints.NORTH;
        // Creates the Edit Stock button
        this.add(editStock, gbc);

        // Sets the coordinates for the Convert Currency button
        gbc.gridx = 8;
        gbc.anchor = GridBagConstraints.NORTH;
        // Creates the Convert Currency Button
        this.add(currencyButton, gbc);

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
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.gridheight = 5;
        gbc.anchor = GridBagConstraints.NORTH;
        this.add(stocksScrollableList, gbc);
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

            JRadioButton longRadioButton = new JRadioButton("Long");
            JRadioButton shortRadioButton = new JRadioButton("Short");

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(longRadioButton);
            buttonGroup.add(shortRadioButton);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Enter Ticker Symbol:"));
            panel.add(tickerField);
            panel.add(new JLabel("Enter Date (dd/mm/yyyy):"));
            panel.add(dateField);
            panel.add(new JLabel("Enter Amount (USD):"));
            panel.add(amountField);
            panel.add(new JLabel("Select Type of Investment:"));
            panel.add(longRadioButton);
            panel.add(shortRadioButton);

            int result = JOptionPane.showConfirmDialog(this, panel, "Add Stock",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String ticker = tickerField.getText();
                String date = dateField.getText();
                String amountStr = amountField.getText();

                // Determine the selected position type
                String investmentType;
                if (shortRadioButton.isSelected()) {
                    investmentType = "Short";
                } else {
                    investmentType = "Long";
                }

                try {
                    validateAllFieldsOrShowErrorMsg(ticker, date, amountStr, investmentType);
                } catch (ValidationException validationException) {
                    System.out.println("Stock Field Validation Exception Occurred");
                    return;
                }

                addStockController.execute(ticker, date, amountStr, loggedInViewModel.getState().getUserID(), investmentType);
            }


        } if (evt.getSource() == currencyButton) {
            // Handle the "Currency" button click
            JTextField symbolField = new JTextField(10);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("Enter 3-letter symbol of currency to convert to:"));
            panel.add(symbolField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Convert Currency",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String symbol = symbolField.getText();

                try {
                    currencyValidateAllFieldsOrShowErrorMsg(symbol);
                } catch (ValidationException validationException) {
                    System.out.println("Currency Validation Exception Occurred");;
                    return;
                }

                try {
                    currencyController.execute(loggedInViewModel.getState().getCurrentCurrency(), symbol);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }


        }
        if (evt.getSource() == deleteUser){
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


    private void validateAllFieldsOrShowErrorMsg(String ticker, String date, String amountStr, String investementType)
            throws ValidationException {
        if (!ticker.isEmpty() && !date.isEmpty() && !amountStr.isEmpty()) {
            if (!this.stockFieldValidator.isDateStrValid(date)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid date.");
            } else if (!this.stockFieldValidator.isTickerStrValid(ticker)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid ticker symbol.");
            } else if (!this.stockFieldValidator.isAmountStrValid(amountStr)) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid (and non-zero) amount.");
            }else if (!this.stockFieldValidator.isTypeValid(investementType)){
                JOptionPane.showMessageDialog(this, "Please indicate what your investment type");
            } else {
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }

        throw new ValidationException();
    }

    private void currencyValidateAllFieldsOrShowErrorMsg(String symbol) throws ValidationException {
        Set<String> validSymbols = new HashSet<>(Arrays.asList("AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP", "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "FOK", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KID", "KMF", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SOS", "SRD", "SSP", "STN", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TVD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VES", "VND", "VUV", "WST", "XAF", "XCD", "XDR", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"
        ));
        if (!symbol.isEmpty()) {
            if (!validSymbols.contains(symbol.toUpperCase())) {
                JOptionPane.showMessageDialog(this, "Please enter a valid 3-letter currency code.");
            }
            else {return;}
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in the field.");
        }

        throw new ValidationException();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        // Update the net profit value using state data
        netProfitValue.setText(state.getCurrentCurrency() + " " + state.getNetProfit());
        GridBagConstraints gbc = new GridBagConstraints();
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
            gbc.gridx = 8;
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
