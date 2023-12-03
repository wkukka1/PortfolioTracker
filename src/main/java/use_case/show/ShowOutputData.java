package use_case.show;

import org.jfree.chart.ChartPanel;

public class ShowOutputData {
    private ChartPanel panel;

    public ShowOutputData(ChartPanel panel) {
        this.panel = panel;
    }

    public ChartPanel getPanel() {
        return panel;
    }


}
