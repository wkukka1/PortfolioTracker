package entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PortfolioTest {
    private Portfolio portfolio;

    @Before
    public void setup() {
        List<Stock> stockList = new ArrayList<>();
        stockList.add(new Stock("A", LocalDateTime.now(), 1.0, 100.0));
        stockList.add(new Stock("B", LocalDateTime.now(), 1.0, 100.0));
        stockList.add(new Stock("A", LocalDateTime.now(), 1.4, 100.0));

        this.portfolio = new Portfolio(stockList, 1234.5, 1);
    }

    @Test
    public void testGetStockList() {
        assertEquals(3, portfolio.getStockList().size());
    }

    @Test
    public void testSetStockList() {
        List<Stock> newStockList = new ArrayList<>();
        portfolio.setStockList(newStockList);

        assertEquals(newStockList, portfolio.getStockList());
    }

    @Test
    public void testAddStockToStockList() {
        portfolio.addStockToStockList(new Stock("AMZN", LocalDateTime.now(), 10.0,
                123));
        assertEquals(4, portfolio.getStockList().size());
    }

    @Test
    public void testGetNetProfit() {
        assertEquals(1234.5, portfolio.getNetProfit(), 0.001);
    }

    @Test
    public void testSetNetProfit() {
        portfolio.setNetProfit(0);
        assertEquals(0, portfolio.getNetProfit(), 0);
    }

    @Test
    public void testGetUserID() {
        assertEquals(1, portfolio.getUserID());
    }

    @Test
    public void testSetUserID() {
        portfolio.setUserID(123);
        assertEquals(123, portfolio.getUserID());
    }

    @Test
    public void testGenerateTickersToQuantities() {
        Map<String, Double> actual = portfolio.generateTickersToQuantities();
        Map<String, Double> expected = new HashMap<>();
        expected.put("A", 2.4);
        expected.put("B", 1.0);

        assertEquals(expected, actual);
    }
}
