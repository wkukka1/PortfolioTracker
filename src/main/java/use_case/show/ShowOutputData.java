package use_case.show;

import org.jfree.chart.ChartPanel;

public class ShowOutputData {
    private ChartPanel panel;
    private double netProfit;

    public ShowOutputData(ChartPanel panel, double netProfit) {
        this.panel = panel;
        this.netProfit = netProfit;
    }

    public ChartPanel getPanel() {
        return panel;
    }

    public double getNetProfit() {
        return netProfit;
    }

}
