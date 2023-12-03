package interface_adapter.logged_in.show;

import org.jfree.chart.ChartPanel;

public class ShowState {
    private double netProfit = 0.0;
    private ChartPanel panel = null;

    public ShowState(ShowState copy) {
        netProfit = copy.netProfit;
        panel = copy.panel;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ShowState() {}

    public double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public ChartPanel getPanel() {
        return panel;
    }

    public void setPanel(ChartPanel panel) {
        this.panel = panel;
    }
}
