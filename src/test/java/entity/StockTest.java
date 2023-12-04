package entity;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class StockTest {
    private Stock stock;

    @Before
    public void setup() {
        this.stock = new Stock("IBM", LocalDateTime.of(2023, 1, 1, 0, 0),
                10.0, 100.0);
    }

    @Test
    public void getTickerSymbol() {
        assertEquals("IBM", stock.getTickerSymbol());
    }

    @Test
    public void setTickerSymbol() {
        stock.setTickerSymbol("AMZN");
        assertEquals("AMZN", stock.getTickerSymbol());
    }

    @Test
    public void getPurchaseLocalDateTime() {
        assertEquals(LocalDateTime.of(2023, 1, 1, 0, 0),
                stock.getPurchaseLocalDateTime());
    }

    @Test
    public void setPurchaseLocalDateTime() {
        stock.setPurchaseLocalDateTime(LocalDateTime.of(2023, 1, 1, 12, 12));
        assertEquals(LocalDateTime.of(2023, 1, 1, 12, 12),
                stock.getPurchaseLocalDateTime());
    }

    @Test
    public void getQuantity() {
        assertEquals(10.0, stock.getQuantity(), 0);
    }

    @Test
    public void setQuantity() {
        stock.setQuantity(0);
        assertEquals(0, stock.getQuantity(), 0);
    }

    @Test
    public void getTotalValueAtPurchase() {
        assertEquals(100.0, stock.getTotalValueAtPurchase(), 0);
    }

    @Test
    public void setTotalValueAtPurchase() {
        stock.setTotalValueAtPurchase(69.22);
        assertEquals(69.22, stock.getTotalValueAtPurchase(), 0);
    }
}
