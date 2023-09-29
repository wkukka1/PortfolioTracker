package api;

import okhttp3.*;
import org.json.JSONException;

import java.io.IOException;

public class RestClient {
    private static final String REST_URL = "https://random-data-api.com/api/v2/banks";

    public static String getMarketData() throws RuntimeException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(REST_URL) // Add headers here, use String.format to add query params
                .build();
        try {
            Response response = client.newCall(request).execute();
            // we can serialize into json here but idk if we have to

            String strResponse = response.body().string();
            System.out.println(strResponse);
            return strResponse;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String marketDataStr = getMarketData();
    }
}
