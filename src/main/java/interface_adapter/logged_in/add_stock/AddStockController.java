package interface_adapter.logged_in.add_stock;

public class AddStockController {
    private final AddStockInteractor addStockInteractor;

    public AddStockController(AddStockInteractor addStockInteractor) {
        this.addStockInteractor = addStockInteractor;
    }
}
