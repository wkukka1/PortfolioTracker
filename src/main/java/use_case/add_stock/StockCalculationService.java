package use_case.add_stock;

import entity.Stock;
import entity.Short;
import java.io.IOException;

public interface StockCalculationService {
    double calculateNewStockProfitToDate(Stock newStock) throws IOException, IllegalArgumentException;

    double calculateNewShortProfitToDate(Short newShort) throws IOException;
}
