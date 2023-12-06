package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.currency_conversion.CurrencyDataAccessInterface;

import java.io.IOException;


public class ExchangeRateClient implements CurrencyDataAccessInterface {

    private static final String BASE_URL = "https://api.exchangeratesapi.io/v1/latest";

    // Note: API key needs to be stored as environment variable in IDE; set this up locally

    /**
     * Returns a JSONObject that contains the Latest Rates Endpoint exchange rate data from
     * the Exchange Rates API (https://exchangeratesapi.io/documentation/).
     *
     * @param base the three-letter currency code of your preferred base currency.
     * @param symbol = the three-letter currency code of the currency you want to convert to.
     */

    @Override
    public JSONObject getCurrencyInfo(String base, String symbol) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(String.format(BASE_URL + "?access_key=%s&base=%s&symbols=%s",
                        System.getenv("CURRENCY_API_KEY"), base, symbol))
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
