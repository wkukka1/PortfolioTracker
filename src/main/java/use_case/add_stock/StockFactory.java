package use_case.add_stock;

import entity.Investment;
import entity.Stock;
import use_case.show.StockPriceDataAccessInterface;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class StockFactory implements InvestmentFactory{
    private final StockPriceDataAccessInterface stockPriceClientImpl;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public StockFactory(StockPriceDataAccessInterface stockPriceClientImpl) {
        this.stockPriceClientImpl = stockPriceClientImpl;
    }

    @Override
    public Investment createInvestment(AddStockInputData addStockData) throws NoSuchElementException {
        String purchaseDate = addStockData.getPurchaseLocalDateTime().format(formatter);

        try {
            double pastStockClosePrice = stockPriceClientImpl.getStockInfoByDate(
                    addStockData.getTickerSymbol(), purchaseDate).getClose();

            double newStockQuantity = addStockData.getTotalValueAtPurchase() / pastStockClosePrice;

            return new Stock(
                    addStockData.getTickerSymbol(),
                    addStockData.getPurchaseLocalDateTime(),
                    newStockQuantity,
                    addStockData.getTotalValueAtPurchase()
            );
        } catch (IOException | IllegalArgumentException e) {
            // Handle exceptions or throw a custom exception
            throw new NoSuchElementException("Error creating stock: " + e.getMessage());
        }
    }
}
