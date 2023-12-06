package interface_adapter.editStock;

import use_case.editStock.EditStockInputBoundary;
import use_case.editStock.EditStockInputData;

public class EditStockController {
    final EditStockInputBoundary editStockInteractor;

    public EditStockController(EditStockInputBoundary interactor) {
        this.editStockInteractor = interactor;
    }

    public void execute(String tickerSymbol, double newQuantity, String username){
        EditStockInputData input = new EditStockInputData(tickerSymbol, newQuantity, username);
        editStockInteractor.execute(input);
    }
}
