package data_access;

import entity.Portfolio;
import entity.Stock;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import use_case.PortfolioDataAccessInterface;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilePortfolioDataAccessObjectTest {
    private PortfolioDataAccessInterface filePortfolioDataAccessObject;

    @Before
    public void setup() throws Exception {
        this.filePortfolioDataAccessObject = new FilePortfolioDataAccessObject("./portfolios_test.csv");
        filePortfolioDataAccessObject.savePortfolio(new Portfolio(new ArrayList<>(), 0, 1));
    }

    @After
    public void teardown() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("./portfolios_test.csv"));
        writer.write("");
    }

    @Test
    public void savePortfolio() {
        Portfolio expected = new Portfolio(new ArrayList<>(), 0, 2);
        filePortfolioDataAccessObject.savePortfolio(expected);
        Portfolio actual = filePortfolioDataAccessObject.getPortfolioByID(expected.getUserID());

        assertEquals(actual, expected);
    }

    @Test
    public void getPortfolioByID() {
        Portfolio expected = new Portfolio(new ArrayList<>(), 0, 1);
        Portfolio actual = filePortfolioDataAccessObject.getPortfolioByID(expected.getUserID());

        assertTrue(expected.getStockList().equals(actual.getStockList()) && expected.getNetProfit() ==
                actual.getNetProfit() && expected.getUserID() == expected.getUserID());
    }

    @Test
    public void addStockToPortfolioByID() {
        Stock newStock = new Stock("AAPL", LocalDateTime.now(), 1.0, 100.0);
        filePortfolioDataAccessObject.addStockToPortfolioByID(1, newStock, 123.0);

        assertEquals(newStock, filePortfolioDataAccessObject.getPortfolioByID(1).getStockList().get(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void testAddStockToPortfolioByIDThrowsNoSuchElementExceptionForUnmappedUserID() {
        Stock newStock = new Stock("AAPL", LocalDateTime.now(), 1.0, 100.0);
        filePortfolioDataAccessObject.addStockToPortfolioByID(2, newStock, 123.0);
    }

    @Test
    public void deletePortfolio() throws Exception {
        filePortfolioDataAccessObject.deletePortfolio(1);

        BufferedReader reader = new BufferedReader(new FileReader("./portfolios_test.csv"));
        reader.readLine();

        assertTrue(StringUtils.isEmpty(reader.readLine()));
    }
}
