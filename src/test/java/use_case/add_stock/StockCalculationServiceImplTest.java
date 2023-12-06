package use_case.add_stock;

import data_access.StockInfoClient;
import entity.AVTimeSeriesDailyResponse;
import entity.Stock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class StockCalculationServiceImplTest {
    private final StockInfoClient mockClient = Mockito.mock(StockInfoClient.class);
    private StockCalculationService mockStockCalculationServiceImpl;
    private MockedStatic<LocalDateTime> mockLocalDateTime;

    @Before
    public void setup() {
        this.mockStockCalculationServiceImpl = new StockCalculationServiceImpl(mockClient);
        this.mockLocalDateTime = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS);
    }

    @After
    public void teardown() {
        mockLocalDateTime.close();
    }

    @Test
    public void testCalculateNewStockProfitToDateForNonSatSunMonDays() throws IOException {
        Stock newStock = new Stock("AAPL", LocalDateTime.now(), 1.0, 100.0);
        AVTimeSeriesDailyResponse mockRes = new AVTimeSeriesDailyResponse("", 1.0, 1.0, 1.0,
                420.0, 1);

        Mockito.when(mockClient.getStockInfoByDate(anyString(), anyString())).thenReturn(mockRes);
        double actual = mockStockCalculationServiceImpl.calculateNewStockProfitToDate(newStock);

        assertEquals(320.0, actual, 0);
    }

    @Test
    public void testCalculateNewStockProfitToDateForMondays() throws IOException {
        Stock newStock = new Stock("AAPL", LocalDateTime.now(), 1.0, 100.0);
        AVTimeSeriesDailyResponse mockRes = new AVTimeSeriesDailyResponse("", 1.0, 1.0, 1.0,
                69.0, 1);
        LocalDateTime mockDate = LocalDateTime.of(2023, 12, 4, 0, 0);

        mockLocalDateTime.when(LocalDateTime::now).thenReturn(mockDate);
        Mockito.when(mockClient.getStockInfoByDate(anyString(), anyString())).thenReturn(mockRes);

        double actual = mockStockCalculationServiceImpl.calculateNewStockProfitToDate(newStock);
        assertEquals(-31.0, actual, 0);
    }

    @Test
    public void testCalculateNewStockProfitToDateForSundays() throws IOException {
        Stock newStock = new Stock("AAPL", LocalDateTime.now(), 1.0, 100.0);
        AVTimeSeriesDailyResponse mockRes = new AVTimeSeriesDailyResponse("", 1.0, 1.0, 1.0,
                69.0, 1);
        LocalDateTime mockDate = LocalDateTime.of(2023, 12, 3, 0, 0);

        mockLocalDateTime.when(LocalDateTime::now).thenReturn(mockDate);
        Mockito.when(mockClient.getStockInfoByDate(anyString(), anyString())).thenReturn(mockRes);

        double actual = mockStockCalculationServiceImpl.calculateNewStockProfitToDate(newStock);
        assertEquals(-31.0, actual, 0);
    }

    @Test
    public void testCalculateNewStockProfitToDateForSaturdays() throws IOException {
        Stock newStock = new Stock("AAPL", LocalDateTime.now(), 1.0, 100.0);
        AVTimeSeriesDailyResponse mockRes = new AVTimeSeriesDailyResponse("", 1.0, 1.0, 1.0,
                69.0, 1);
        LocalDateTime mockDate = LocalDateTime.of(2023, 12, 2, 0, 0);

        mockLocalDateTime.when(LocalDateTime::now).thenReturn(mockDate);
        Mockito.when(mockClient.getStockInfoByDate(anyString(), anyString())).thenReturn(mockRes);

        double actual = mockStockCalculationServiceImpl.calculateNewStockProfitToDate(newStock);
        assertEquals(-31.0, actual, 0);
    }
}
