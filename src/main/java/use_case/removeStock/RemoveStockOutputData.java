package use_case.removeStock;

import java.util.Map;

public class RemoveStockOutputData {
    private Map<String, Double> tickersToQuantities;

    public RemoveStockOutputData(Map<String, Double> tickersToQuantities) {
        this.tickersToQuantities = tickersToQuantities;
    }
    public Map<String, Double> getTickersToQuantities() {
        return tickersToQuantities;
    }
}
