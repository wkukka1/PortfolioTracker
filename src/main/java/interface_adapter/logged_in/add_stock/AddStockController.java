package interface_adapter.logged_in.add_stock;

public class AddStockController {
    private final AddStockInputBoundary addStockInteractor;

    public AddStockController(AddStockInputBoundary addStockInteractor) {
        this.addStockInteractor = addStockInteractor;
    }

    public void execute(String ticker, String date, String amount) {
        
    }
}
