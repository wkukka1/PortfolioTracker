package use_case.currency_conversion;

import org.json.JSONObject;

public interface CurrencyDataAccessInterface {

    JSONObject getCurrencyInfo(String base, String symbol);
}
