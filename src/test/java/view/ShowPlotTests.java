package view;


import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.StockInfoClient;
import entity.*;
import interface_adapter.logged_in.show.ShowPresenter;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import use_case.show.*;
import use_case.PortfolioDataAccessInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ShowPlotTests {
    private final StockPriceDataAccessInterface mockStockClient = Mockito.mock(StockInfoClient.class);
    private final PortfolioDataAccessInterface mockPortfolioDAO = Mockito.mock(FilePortfolioDataAccessObject.class);
    private final ShowOutputBoundary mockPresenter = Mockito.mock(ShowPresenter.class);
    private ShowInputBoundary showInteractor;
    private String jsonString = "{\"Time Series (Daily)\":{\"2023-11-10\":{\"low\":\"153.5250\",\"volume\":\"3982683\"," +
            "\"open\":\"155.8500\",\"high\":\"161.2200\",\"4. close\":\"160.4000\"},\"2023-08-07\":{\"low\":\"163.6200\"" +
            ",\"volume\":\"3540487\",\"open\":\"168.0100\",\"high\":\"168.6100\",\"4. close\":\"166.6700\"},\"2023-11-09\"" +
            ":{\"low\":\"156.1100\",\"volume\":\"3982258\",\"open\":\"159.5000\",\"high\":\"162.3980\",\"4. close\":" +
            "\"156.3500\"},\"2023-08-08\":{\"low\":\"154.1700\",\"volume\":\"11725254\",\"open\":\"157.2300\",\"high\":" +
            "\"160.7200\",\"4. close\":\"156.3300\"},\"2023-08-03\":{\"low\":\"159.5300\",\"volume\":\"7812111\",\"open\":" +
            "\"163.4600\",\"high\":\"164.7200\",\"4. close\":\"161.0500\"}}}";


    private void addUser(int numOfUsers) {
        UserFactory uf = new CommonUserFactory();
        FileUserDataAccessObject fudao;
        FilePortfolioDataAccessObject fpdao;
        try {
            fudao = new FileUserDataAccessObject("./users.csv", uf);
            fpdao = new FilePortfolioDataAccessObject("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < numOfUsers; i++) {
            fudao.save(uf.create("user" + i, "password" + i, LocalDateTime.now(), i));
            fpdao.savePortfolio(new Portfolio(new ArrayList<>(), 0, i));
        }
    }
    @Before
    public void setup(){
        this.showInteractor = new ShowInteractor((ShowPortfolioDataAccessInterface) mockPortfolioDAO, mockStockClient,
                mockPresenter);
    }

    @Test
    public void testPlotOneStock() throws IOException {
        addUser(1);
        ShowInputData mockInputData = new ShowInputData(0);
        JSONObject mockClientRes = new JSONObject(jsonString);
        String firstDateKey = mockClientRes.getJSONObject("Time Series (Daily)").keySet().iterator().next();

        // Parse the JSON string into a HashMap
        JSONObject timeSeriesDaily = mockClientRes.getJSONObject("Time Series (Daily)");
        HashMap<String, HashMap<String, String>> mockHashMap = new HashMap<>();
        for (String date : timeSeriesDaily.keySet()) {
            JSONObject innerObject = timeSeriesDaily.getJSONObject(date);
            HashMap<String, String> innerMap = new HashMap<>();
            for (String key : innerObject.keySet()) {
                innerMap.put(key, innerObject.getString(key));
            }
            mockHashMap.put(date, innerMap);
        }

        LocalDateTime firstDate = LocalDateTime.parse(firstDateKey + "T00:00:00");

        LocalDateTime oneMonthBehind = firstDate.minusMonths(1);

        Stock stock = new Stock("AAPL", oneMonthBehind, 1, 80);
        List<Investment> stockList = List.of(stock);
        Portfolio mockPortfolioRes = new Portfolio(stockList, 0, 0);

        // Set the mockHashMap as the return value for getStockInfo
        when(mockStockClient.getStockInfo(anyString())).thenReturn(mockClientRes);
        // Set the mockPortfolioRes as the return value for getPortfolioByID
        when(mockPortfolioDAO.getPortfolioByID(anyInt())).thenReturn(mockPortfolioRes);

        // Execute the showInteractor
        showInteractor.execute(mockInputData);

        // Verify the prepareSuccessView method is called once
        verify(mockPresenter, times(1)).prepareSuccessView(any(ShowOutputData.class));
    }

}
