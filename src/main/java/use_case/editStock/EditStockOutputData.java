package use_case.editStock;

import entity.Stock;

public class EditStockOutputData {
    private Stock stock;

    EditStockOutputData(Stock stock){
        this.stock = stock;
    }

    public Stock getStock(){
        return this.stock;
    }
}
