package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.show.StockPriceDataAccessInterface;

import java.io.IOException;


public class MarketStackClient implements StockPriceDataAccessInterface {
    // todo: use https://www.alphavantage.co/documentation/ instead?
    private static final String BASE_URL = "http://api.marketstack.com/v1/eod";
    // Note: API key needs to be stored as environment variable in IDE; set this up locally

    /**
     * Returns a JSONObject that contains the end-of-day stock market data from
     * the MarketStack API.
     *
     * @param symbol The ticker symbol of the stock whose data is to be accessed.
     * @param date_from From-date in YYYY-MM-DD format
     * @param date_to End-date in YYYY-MM-DD format
     */
    public JSONObject getStockInfo(String symbol, String date_from, String date_to) {
        // todo: make it accept LocalDateTime objects
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(String.format(BASE_URL + "?access_key=%s&symbols=%s&date_from=%s&date_to=%s",
                        System.getenv("API_KEY"), symbol, date_from, date_to))
                .build();

        try {
            Response response = client.newCall(request).execute();
            JSONObject responseBody = new JSONObject(response.body().string());

            System.out.println("HTTP Status: " + response.code());
            System.out.println("JSON Response String: " + responseBody);

            return responseBody;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }
}