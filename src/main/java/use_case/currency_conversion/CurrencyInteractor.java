package use_case.currency_conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class CurrencyInteractor implements CurrencyInputBoundary {
    final CurrencyDataAccessInterface currencyDataAccessObject;
    final CurrencyOutputBoundary currencyPresenter;

    public CurrencyInteractor(CurrencyDataAccessInterface currencyDataAccessObject, CurrencyOutputBoundary currencyPresenter) {
        this.currencyDataAccessObject = currencyDataAccessObject;
        this.currencyPresenter = currencyPresenter;
    }

    @Override
    public void execute(CurrencyInputData currencyInputData) throws JsonProcessingException {
        JSONObject rawCurrencyInfo = currencyDataAccessObject.getCurrencyInfo(currencyInputData.getCurrencyFrom(), currencyInputData.getCurrencyTo());
        HashMap processedStockInfo = jsonToHashMap(rawCurrencyInfo);
        LinkedHashMap conversionRates = (LinkedHashMap) processedStockInfo.get("conversion_rates");
        double conversion;
        try {
            conversion = (Double) conversionRates.get(currencyInputData.getCurrencyFrom());
        }catch (Exception e) {
            conversion = 1.0;
        }

        CurrencyOutputData currencyOutputData = new CurrencyOutputData(conversion, currencyInputData.getCurrencyTo());
        currencyPresenter.prepareSuccessView(currencyOutputData);
    }

    public HashMap jsonToHashMap(JSONObject rawCurrencyInfo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(rawCurrencyInfo.toString(), HashMap.class);
    }
}
