package use_case.removeStock;

import entity.Stock;
import java.util.List;
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

    public void setNewStockProfitToDate(double newStockProfitToDate) {
        this.newStockProfitToDate = newStockProfitToDate;
    }

    public Map<String, Double> getTickersToQuantities() {
        return tickersToQuantities;
    }

    public void setTickersToQuantities(Map<String, Double> tickersToQuantities) {
        this.tickersToQuantities = tickersToQuantities;
    }
}
