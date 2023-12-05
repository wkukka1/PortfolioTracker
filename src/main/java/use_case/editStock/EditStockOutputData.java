package use_case.editStock;

import entity.Stock;

import java.util.Map;

public class EditStockOutputData {
    private Stock stock;

    private Map<String, Double> tickersToAggregatedQuantities;
    EditStockOutputData(Stock stock, Map<String, Double> tickersToAggregatedQuantities){
        this.stock = stock;
        this.tickersToAggregatedQuantities = tickersToAggregatedQuantities;
    }

    public Stock getStock(){
        return this.stock;
    }

    public Map<String, Double> getTickersToAggregatedQuantities(){
        return this.tickersToAggregatedQuantities;
    }
}
