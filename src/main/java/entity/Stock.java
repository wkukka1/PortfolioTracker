package entity;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Stock {
    private String tickerSymbol;
    private LocalDateTime purchaseLocalDateTime;
    private HashMap<String, Double> quantities;
    private double totalValueAtPurchase;

    public Stock(String tickerSymbol, LocalDateTime purchaseLocalDateTime, double quantity, double totalValueAtPurchase) {
        this.tickerSymbol = tickerSymbol;
        this.purchaseLocalDateTime = purchaseLocalDateTime;
        this.quantities = new HashMap<>();
        addQuantity(quantity);
        this.totalValueAtPurchase = totalValueAtPurchase;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public LocalDateTime getPurchaseLocalDateTime() {
        return purchaseLocalDateTime;
    }

    public void setPurchaseLocalDateTime(LocalDateTime purchaseLocalDateTime) {
        this.purchaseLocalDateTime = purchaseLocalDateTime;
    }

    public double getQuantity(String date) {
        return quantities.get(date);
    }

    public void addQuantity(double quantity) {
        quantities.put(localDateTimeToyyyymmdd(LocalDateTime.now()), quantity);
    }

    private String localDateTimeToyyyymmdd(LocalDateTime now) {
        //todo: implement
    }

    public double getTotalValueAtPurchase() {
        return totalValueAtPurchase;
    }

    public void setTotalValueAtPurchase(double totalValueAtPurchase) {
        this.totalValueAtPurchase = totalValueAtPurchase;
    }
}
