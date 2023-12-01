package interface_adapter.editStock;

import use_case.editStock.EditStockInputBoundary;

public class EditStockController {
    final EditStockInputBoundary editStockInteractor;

    public EditStockController(EditStockInputBoundary interactor) {
        this.editStockInteractor = interactor;
    }
}
