package use_case.show;
import entity.AVTimeSeriesDailyResponse;
import java.io.IOException;
import org.json.JSONObject;

public interface StockPriceDataAccessInterface {
    JSONObject getStockInfo(String symbol);
    AVTimeSeriesDailyResponse getStockInfoByDate(String symbol, String date) throws IOException, IllegalArgumentException;
}
