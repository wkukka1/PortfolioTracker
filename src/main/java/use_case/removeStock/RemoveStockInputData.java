package use_case.removeStock;
import entity.Stock;

public class RemoveStockInputData {
    final private Stock stock;

    public RemoveStockInputData(Stock stock){
        this.stock = stock;
    }

    public Stock getStock(){return this.stock;}
}
