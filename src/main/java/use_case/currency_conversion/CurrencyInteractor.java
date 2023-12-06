package use_case.currency_conversion;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Portfolio;
import entity.Stock;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.json.JSONObject;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        HashMap<String, String> processedStockInfo = jsonToHashMap(rawCurrencyInfo);
        CurrencyOutputData currencyOutputData = new CurrencyOutputData(Double.valueOf(processedStockInfo.get(currencyInputData.getCurrencyTo())));
        currencyPresenter.prepareSuccessView(currencyOutputData);
    }

    public HashMap<String, String> jsonToHashMap(JSONObject rawCurrencyInfo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap rawMap = mapper.readValue(rawCurrencyInfo.toString(), HashMap.class);
        return (HashMap<String, String>) rawMap.get("rates");

    }
}
