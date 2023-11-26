package interface_adapter.logged_in.add_stock;

import use_case.add_stock.AddStockInputBoundary;

public class AddStockController {
    private final AddStockInputBoundary addStockInteractor;

    public AddStockController(AddStockInputBoundary addStockInteractor) {
        this.addStockInteractor = addStockInteractor;
    }

    public void execute(String ticker, String date, String amount) {
        
    }
}
