package use_case.removeStock;

import entity.Stock;
import java.util.List;
public class RemoveStockOutputData {
    private final List<Stock> stockList;

    public RemoveStockOutputData(List<Stock> stockList){
        this.stockList = stockList;
    }

    public List<Stock> getStockList(){
        return this.stockList;
    }
}
