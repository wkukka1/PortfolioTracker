package view;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A panel containing a label and a text field.
 */
public class LabelTextPanel extends JPanel {
    private JLabel label;
    private JTextField textField;
    private PropertyChangeSupport pcs;
    LabelTextPanel(JLabel label, JTextField textField) {
        this.label = label;
        this.textField = textField;
        this.pcs = new PropertyChangeSupport(this);
        this.add(label);
        this.add(textField);
    }

    public JLabel getLabel() {
        return label;
    }

    public void setText(String text) {
        String oldValue = textField.getText();
        textField.setText(text);
        String newValue = textField.getText();
        pcs.firePropertyChange("text", oldValue, newValue);
    }

    public String getText() {
        return textField.getText();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
