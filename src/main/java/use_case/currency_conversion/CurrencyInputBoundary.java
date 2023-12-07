package use_case.currency_conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

import java.util.HashMap;

public interface CurrencyInputBoundary {
    void execute(CurrencyInputData currencyInputData) throws JsonProcessingException;

    HashMap jsonToHashMap(JSONObject any) throws JsonProcessingException;
}
