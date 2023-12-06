package use_case.currency_conversion;

import entity.AVTimeSeriesDailyResponse;
import org.json.JSONObject;

import java.io.IOException;

public interface CurrencyDataAccessInterface {

    JSONObject getCurrencyInfo(String base, String symbol);
}
