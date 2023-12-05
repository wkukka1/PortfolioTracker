package use_case.add_stock;

import entity.Stock;

import java.io.IOException;

public interface StockCalculationService {
    double calculateNewStockProfitToDate(Stock newStock) throws IOException, IllegalArgumentException;
}
