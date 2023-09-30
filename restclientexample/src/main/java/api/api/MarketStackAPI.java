package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MarketStackAPI {

    public static void main(String[] args) {
        String apiKey = System.getenv("API_KEY");
        String symbol = "AAPL";

        try {
            URL url = new URL("http://api.marketstack.com/v1/eod" +
                    "?access_key=" + apiKey +
                    "&symbols=" + symbol);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                System.out.println("API Response:");
                System.out.println(response.toString());
            } else {
                System.out.println("API request failed with response code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
