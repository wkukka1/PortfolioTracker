package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.show.StockPriceDataAccessInterface;

import java.io.IOException;


public class StockInfoClient implements StockPriceDataAccessInterface {
    private static final String BASE_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY";
    // Note: API key needs to be stored as environment variable in IDE; set this up locally

    /**
     * Returns a JSONObject that contains the TIME_SERIES_DAILY stock data (full-length time) from
     * the Alpha Vantage API (https://www.alphavantage.co/documentation/).
     *
     * @param symbol The ticker symbol of the stock whose data is to be accessed.
     */
    public JSONObject getStockInfo(String symbol) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(String.format(BASE_URL + "&symbol=%s&outputsize=full&apikey=%s",
                        symbol, System.getenv("API_KEY")))
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