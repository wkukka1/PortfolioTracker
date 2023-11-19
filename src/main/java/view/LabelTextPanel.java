package view;

import javax.swing.*;

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {
    private JLabel label;
    private JTextField textField;
    LabelTextPanel(JLabel label, JTextField textField) {
        this.label = label;
        this.textField = textField;
        this.add(label);
        this.add(textField);
    }

    public JLabel getLabel() {
        return label;
    }

    public void setText(String text){
        textField.setText(text);
    }
}
