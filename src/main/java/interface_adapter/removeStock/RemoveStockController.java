package interface_adapter.removeStock;

import use_case.removeStock.RemoveStockInputBoundary;
import entity.Stock;
import use_case.removeStock.RemoveStockInputData;

public class RemoveStockController {
    final RemoveStockInputBoundary removeStockInteractor;

    public RemoveStockController(RemoveStockInputBoundary removeStockInteractor){
        this.removeStockInteractor = removeStockInteractor;
    }

    public void execute(Stock stock){
        RemoveStockInputData input = new RemoveStockInputData(stock);
        removeStockInteractor.execute(input);
    }
}
