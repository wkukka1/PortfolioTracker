package use_case.add_stock;

import data_access.FilePortfolioDataAccessObject;
import data_access.StockInfoClient;
import entity.AVTimeSeriesDailyResponse;
import entity.Portfolio;
import entity.Short;
import entity.Stock;
import interface_adapter.logged_in.add_stock.AddStockPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import use_case.show.StockPriceDataAccessInterface;
import use_case.PortfolioDataAccessInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AddStockInteractorTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final StockPriceDataAccessInterface mockStockClient = Mockito.mock(StockInfoClient.class);
    private final PortfolioDataAccessInterface mockPortfolioDAO = Mockito.mock(FilePortfolioDataAccessObject.class);
    private final AddStockOutputBoundary mockPresenter = Mockito.mock(AddStockPresenter.class);
    private final StockCalculationService mockStockCalcService = Mockito.mock(StockCalculationServiceImpl.class);
    private AddStockInputBoundary addStockInteractor;

    @Before
    public void setUp() {
        this.addStockInteractor = new AddStockInteractor(mockStockClient, mockPortfolioDAO, mockPresenter,
                mockStockCalcService);
    }

    @Test
    public void testValidAddStockCallCallsNecessaryServices() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(formatter);
        AddStockInputData mockInputData = new AddStockInputData("AAPL", now,
                200.0, 1, "Long");
        AVTimeSeriesDailyResponse mockClientRes = new AVTimeSeriesDailyResponse("2023-12-01", 1, 1,
                1, 200.0, 1);
        Portfolio mockPortfolioRes = new Portfolio(new ArrayList<>(), 0, 1);
        Map<String, Double> mockTToQ = new HashMap<>();
        mockTToQ.put("AAPL", 1.0);

        when(mockStockClient.getStockInfoByDate(anyString(), anyString())).thenReturn(mockClientRes);
        when(mockStockCalcService.calculateNewStockProfitToDate(any(Stock.class))).thenReturn(69.0);
        when(mockPortfolioDAO.getPortfolioByID(anyInt())).thenReturn(mockPortfolioRes);

        addStockInteractor.addStock(mockInputData);
        verify(mockStockClient, times(1)).getStockInfoByDate("AAPL", nowStr);
        verify(mockStockCalcService, times(1)).calculateNewStockProfitToDate(any(Stock.class));
        verify(mockPortfolioDAO, times(2)).getPortfolioByID(1);
        verify(mockPortfolioDAO, times(1)).addStockToPortfolioByID(anyInt(), any(Stock.class),
                anyDouble());
        verify(mockPresenter, times(1)).prepareSuccessView(any(AddStockOutputData.class));
    }

    @Test
    public void testValidAddShortCallCallsNecessaryServices() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(formatter);
        AddStockInputData mockInputData = new AddStockInputData("AAPL", now,
                200.0, 1, "Short");
        AVTimeSeriesDailyResponse mockClientRes = new AVTimeSeriesDailyResponse("2023-12-01", 1, 1,
                1, 200.0, 1);
        Portfolio mockPortfolioRes = new Portfolio(new ArrayList<>(), 0, 1);
        Map<String, Double> mockTToQ = new HashMap<>();
        mockTToQ.put("AAPL", 1.0);

        when(mockStockClient.getStockInfoByDate(anyString(), anyString())).thenReturn(mockClientRes);
        when(mockStockCalcService.calculateNewShortProfitToDate(any(Short.class))).thenReturn(69.0);
        when(mockPortfolioDAO.getPortfolioByID(anyInt())).thenReturn(mockPortfolioRes);

        addStockInteractor.addStock(mockInputData);
        verify(mockStockClient, times(1)).getStockInfoByDate("AAPL", nowStr);
        verify(mockStockCalcService, times(1)).calculateNewShortProfitToDate(any(Short.class));
        verify(mockPortfolioDAO, times(2)).getPortfolioByID(1);
        verify(mockPortfolioDAO, times(1)).addStockToPortfolioByID(anyInt(), any(Short.class),
                anyDouble());
        verify(mockPresenter, times(1)).prepareSuccessView(any(AddStockOutputData.class));
    }

    @Test
    public void testAddStockUnsuccessViewOnIOException() throws IOException {
        AddStockInputData mockInputData = new AddStockInputData("AAPL", LocalDateTime.now(),
                200.0, 1, "Long");

        when(mockStockClient.getStockInfoByDate(anyString(), anyString())).thenThrow(IOException.class);

        addStockInteractor.addStock(mockInputData);
        verify(mockPresenter, times(1)).prepareNonSuccessView(anyString());
    }

    @Test
    public void testAddStockUnsuccessViewOnIllegalArgException() throws IOException {
        AddStockInputData mockInputData = new AddStockInputData("AAPL", LocalDateTime.now(),
                200.0, 1, "Long");

        when(mockStockClient.getStockInfoByDate(anyString(), anyString())).thenThrow(IllegalArgumentException.class);

        addStockInteractor.addStock(mockInputData);
        verify(mockPresenter, times(1)).prepareNonSuccessView(anyString());
    }
}
