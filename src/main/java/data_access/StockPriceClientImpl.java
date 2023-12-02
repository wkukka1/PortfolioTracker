package data_access;

import entity.AVTimeSeriesDailyResponse;
import entity.deserializer.AVTimeSeriesDailyDeserializer;
import entity.deserializer.AVTimeSeriesDailyDeserializerImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import use_case.show.StockPriceDataAccessInterface;

import java.io.IOException;


public class StockPriceClientImpl implements StockPriceDataAccessInterface {
    private final AVTimeSeriesDailyDeserializer avTimeSeriesDailyDeserializer = new AVTimeSeriesDailyDeserializerImpl();
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private static final String META_DATA_MARKER = "Meta Data";

    public AVTimeSeriesDailyResponse getStockInfoByDate(String symbol, String date) throws RuntimeException {
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
            }

            AVTimeSeriesDailyResponse res = avTimeSeriesDailyDeserializer.deserialize(responseBody, date);
            return res;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
