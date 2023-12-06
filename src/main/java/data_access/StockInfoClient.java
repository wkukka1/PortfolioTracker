package data_access;

import entity.AVTimeSeriesDailyResponse;
import entity.deserializer.AVTimeSeriesDailyDeserializer;
import entity.deserializer.AVTimeSeriesDailyDeserializerImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.show.StockPriceDataAccessInterface;

import java.io.IOException;
import java.util.NoSuchElementException;


public class StockInfoClient implements StockPriceDataAccessInterface {

    private final AVTimeSeriesDailyDeserializer avTimeSeriesDailyDeserializer = new AVTimeSeriesDailyDeserializerImpl();
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private static final String META_DATA_MARKER = "Meta Data";
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
                .url(String.format(BASE_URL + "?function=TIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s",
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

    public AVTimeSeriesDailyResponse getStockInfoByDate(String symbol, String date) throws IOException,
            IllegalArgumentException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url(String.format(BASE_URL + "?function=TIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s",
                        symbol, System.getenv("API_KEY")))
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            if (!responseBody.contains(META_DATA_MARKER)) {
                throw new IOException();
            } else if (!responseBody.contains(date)) {
                throw new NoSuchElementException();
            }

            AVTimeSeriesDailyResponse res = avTimeSeriesDailyDeserializer.deserialize(responseBody, date);
            return res;
        } catch (IOException | JSONException e) {
            throw new IOException(e);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e);
        }
    }
}