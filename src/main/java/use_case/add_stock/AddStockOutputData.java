package use_case.add_stock;

import java.util.Map;

public class AddStockOutputData {
    private double newStockProfitToDate;
    private Map<String, Double> tickersToQuantities;

    public AddStockOutputData(double newStockProfitToDate, Map<String, Double> tickersToQuantities) {
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
