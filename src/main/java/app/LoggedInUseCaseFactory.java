package app;

import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logged_in.add_stock.AddStockController;
import use_case.add_stock.AddStockInputBoundary;
import use_case.add_stock.AddStockInteractor;
import view.LoggedInView;
import view.validation.StockFieldValidator;
import view.validation.StockFieldValidatorImpl;

public class LoggedInUseCaseFactory {
    private LoggedInUseCaseFactory() {
    }

    public static LoggedInView create(LoggedInViewModel loggedInViewModel) {
        AddStockController addStockController = createAddStockUseCase();
        StockFieldValidator stockFieldValidator = new StockFieldValidatorImpl();

        return new LoggedInView(loggedInViewModel, stockFieldValidator, addStockController);
    }

    private static AddStockController createAddStockUseCase() {
        AddStockInputBoundary addStockInteractor = new AddStockInteractor();
        return new AddStockController(addStockInteractor);
    }
}
