package use_case.currency_conversion;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CurrencyInputBoundary {
    void execute(CurrencyInputData currencyInputData) throws JsonProcessingException;

}
