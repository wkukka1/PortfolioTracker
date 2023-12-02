package use_case.show;
import entity.AVTimeSeriesDailyResponse;

public interface StockPriceDataAccessInterface {
    AVTimeSeriesDailyResponse getStockInfoByDate(String symbol, String date) throws RuntimeException;
}
