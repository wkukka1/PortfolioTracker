package view;

import javax.swing.*;

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {
    private JLabel label;
    LabelTextPanel(JLabel label, JTextField textField) {
        this.label = label;
        this.add(label);
        this.add(textField);
    }

    public JLabel getLabel() {
        return label;
    }
}
