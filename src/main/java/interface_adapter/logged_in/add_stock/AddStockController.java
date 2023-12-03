package interface_adapter.logged_in.add_stock;

import use_case.add_stock.AddStockInputBoundary;
import use_case.add_stock.AddStockInputData;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddStockController {
    private final AddStockInputBoundary addStockInteractor;

    public AddStockController(AddStockInputBoundary addStockInteractor) {
        this.addStockInteractor = addStockInteractor;
    }

    public void execute(String ticker, String date, String amount, int userID) {
        String[] dateComponents = date.split("/");
        LocalDateTime purchaseLocalDateTime = LocalDate.of(Integer.parseInt(dateComponents[2]),
                Integer.parseInt(dateComponents[1]), Integer.parseInt(dateComponents[0])).atStartOfDay();

        AddStockInputData addStockInputData = new AddStockInputData(ticker.toUpperCase(),
                purchaseLocalDateTime, Double.parseDouble(amount), userID);

        addStockInteractor.addStock(addStockInputData);
    }
}
