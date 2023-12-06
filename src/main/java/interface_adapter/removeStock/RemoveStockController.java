package interface_adapter.removeStock;

import use_case.removeStock.RemoveStockInputBoundary;
import entity.Stock;
import use_case.removeStock.RemoveStockInputData;

public class RemoveStockController {
    final RemoveStockInputBoundary removeStockInteractor;

    public RemoveStockController(RemoveStockInputBoundary removeStockInteractor){
        this.removeStockInteractor = removeStockInteractor;
    }

    public void execute(String ticker, String username){
        RemoveStockInputData input = new RemoveStockInputData(ticker, username);
        removeStockInteractor.execute(input);
    }
}
