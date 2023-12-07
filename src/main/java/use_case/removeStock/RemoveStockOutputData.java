package use_case.removeStock;

import java.util.Map;

public class RemoveStockOutputData {
    private double newStockProfitToDate;
    private Map<String, Double> tickersToQuantities;

    public RemoveStockOutputData(double newStockProfitToDate, Map<String, Double> tickersToQuantities) {
        this.newStockProfitToDate = newStockProfitToDate;
        this.tickersToQuantities = tickersToQuantities;
    }

    public double getNewStockProfitToDate() {
        return newStockProfitToDate;
    }

    public Map<String, Double> getTickersToQuantities() {
        return tickersToQuantities;
    }
}
