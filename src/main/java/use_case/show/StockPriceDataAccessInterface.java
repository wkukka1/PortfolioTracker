package use_case.show;
import org.json.JSONObject;

public interface StockPriceDataAccessInterface {
    public JSONObject getStockInfo(String symbol, String date_from, String date_to);
}
