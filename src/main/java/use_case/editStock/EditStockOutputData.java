package use_case.editStock;

import entity.Stock;

import java.util.Map;

public class EditStockOutputData {

    private Map<String, Double> tickersToAggregatedQuantities;
    EditStockOutputData(Map<String, Double> tickersToAggregatedQuantities){
        this.tickersToAggregatedQuantities = tickersToAggregatedQuantities;
    }


    public Map<String, Double> getTickersToAggregatedQuantities(){
        return this.tickersToAggregatedQuantities;
    }
}
