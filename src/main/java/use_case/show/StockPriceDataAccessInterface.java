package use_case.show;
import entity.AVTimeSeriesDailyResponse;

import java.io.IOException;

public interface StockPriceDataAccessInterface {
    AVTimeSeriesDailyResponse getStockInfoByDate(String symbol, String date) throws IOException,
            IllegalArgumentException;
}
