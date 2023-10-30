import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockManager {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Stock Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        // Create a panel to hold the components
        JPanel panel = new JPanel();

        // Create the "Add Stock" button
        JButton addButton = new JButton("Add Stock");

        // Add an ActionListener to the button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a dialog for user input
                JTextField tickerField = new JTextField(10);
                JTextField amountField = new JTextField(10);
                JTextField dateField = new JTextField(10);

                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(3, 2));
                inputPanel.add(new JLabel("Ticker:"));
                inputPanel.add(tickerField);
                inputPanel.add(new JLabel("Amount:"));
                inputPanel.add(amountField);
                inputPanel.add(new JLabel("Date:"));
                inputPanel.add(dateField);

                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Stock Details", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    // Get user input
                    String ticker = tickerField.getText();
                    int amount = Integer.parseInt(amountField.getText());
                    String date = dateField.getText();

                    // Process the stock addition logic here (replace with your code)
                    // For this example, we will just print the values
                    System.out.println("Ticker: " + ticker);
                    System.out.println("Amount: " + amount);
                    System.out.println("Date: " + date);
                }
            }
        });

        // Add the button to the panel
        panel.add(addButton);

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Set the frame to be visible
        frame.setVisible(true);
    }
}
