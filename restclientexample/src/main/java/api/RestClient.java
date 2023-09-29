package api;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RestClient {
    private static final String BASE_URL = "https://query1.finance.yahoo.com/v8/finance/chart";

    public static JSONObject getMarketData(String interval, String range, String companySymbol) throws RuntimeException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(String.format(BASE_URL + "/?interval=%s&range=%s&symbol=%s", interval, range, companySymbol))
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

    public static void main(String[] args) {
        JSONObject marketDataStr = getMarketData("1d", "1d", "GOOG");
    }
}
