package view.components;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;

public class ScrollableStockList extends JPanel {
    private DefaultListModel<Datum> dataModel = new DefaultListModel<>();

    public ScrollableStockList(Map<String, Double> map) {
        DataPanel dataPanel;
        if (map.isEmpty()) {
            dataPanel = new DataPanel(8);
            Datum placeholder1 = new Datum("Placeholder 1", 0.0);
            dataPanel.addDatum(placeholder1);
            dataModel.addElement(placeholder1);

            Datum placeholder2 = new Datum("Placeholder 2", 0.0);
            dataPanel.addDatum(placeholder2);
            dataModel.addElement(placeholder2);

            Datum placeholder3 = new Datum("Placeholder 3", 0.0);
            dataPanel.addDatum(placeholder3);
            dataModel.addElement(placeholder3);
        } else {
            dataPanel = new DataPanel(8);
            for (String ticker : map.keySet()) {
                Datum datum = new Datum(ticker, map.get(ticker));
                dataPanel.addDatum(datum);
                dataModel.addElement(datum);
            }
        }

        JScrollPane scrollPane1 = new JScrollPane(dataPanel);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane1);
    }
}

class DataPanel extends JPanel implements Scrollable {
    private int visibleRowCount = 1;

    public DataPanel(int visibleRowCount) {
        this.visibleRowCount = visibleRowCount;
        setLayout(new GridLayout(0, 1));
    }

    public void addDatum(Datum datum) {
        add(new DatumPanel(datum));
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        if (getComponentCount() > 0) {
            JComponent comp = (JComponent) getComponents()[0];
            int width = 200;
            int height = 100;
            Dimension d = new Dimension(width, height);
            System.out.println(d);
            return d;
        } else {
            return new Dimension(0, 0);
        }
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (getComponentCount() > 0) {
            JComponent comp = (JComponent) getComponents()[0];
            Dimension d = comp.getPreferredSize();
            if (orientation == SwingConstants.VERTICAL) {
                return d.height;
            } else {
                return d.width;
            }
        }
        return 0;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (getComponentCount() > 0) {
            JComponent comp = (JComponent) getComponents()[0];
            Dimension d = comp.getPreferredSize();
            if (orientation == SwingConstants.VERTICAL) {
                return visibleRowCount * d.height;
            } else {
                return d.width;
            }
        }
        return 0;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

}

@SuppressWarnings("serial")
class DatumPanel extends JPanel {
    private static final int GBC_I = 3;
    private Datum datum;
    private JLabel nameLabel = new JLabel();
    private JLabel valueLabel = new JLabel();

    public DatumPanel() {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setLayout(new GridBagLayout());
        add(new JLabel("Ticker:"), createGbc(0, 0));
        setBorder(blackline);
        add(nameLabel, createGbc(1, 0));
        add(new JLabel("Quantity:"), createGbc(0, 1));
        add(valueLabel, createGbc(1, 1));
    }

    public DatumPanel(Datum datum) {
        this();
        setDatum(datum);
    }

    public final void setDatum(Datum datum) {
        this.datum = datum;
        nameLabel.setText(datum.getName());
        valueLabel.setText("" + datum.getValue());
    }

    public Datum getDatum() {
        return datum;
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new Insets(GBC_I, GBC_I, GBC_I, GBC_I);
        gbc.insets.left = x != 0 ? 3 * GBC_I : GBC_I;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        return gbc;
    }
}

class Datum {
    private String name;
    private double value;

    public Datum(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ticker: " + name + "\n");
        sb.append("Quantity: " + value);
        return super.toString();
    }

}