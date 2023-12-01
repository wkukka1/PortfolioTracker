package use_case.show;
import org.json.JSONObject;

public interface StockPriceDataAccessInterface {
    JSONObject getStockInfo(String symbol);
}
