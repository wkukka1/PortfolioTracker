package use_case.add_stock;
import entity.Short;
import use_case.show.StockPriceDataAccessInterface;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class ShortFactory {
    private final StockPriceDataAccessInterface stockPriceClientImpl;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public ShortFactory(StockPriceDataAccessInterface stockPriceClientImpl){
        this.stockPriceClientImpl = stockPriceClientImpl;
    }

    public Short createShort(AddStockInputData addStockData) throws NoSuchElementException{
        String purchaseDate = addStockData.getPurchaseLocalDateTime().format(formatter);

        try {
            double pastStockClosePrice = stockPriceClientImpl.getStockInfoByDate(
                    addStockData.getTickerSymbol(), purchaseDate).getClose();

            double newStockQuantity = addStockData.getTotalValueAtPurchase() / pastStockClosePrice;

            return new Short(
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
