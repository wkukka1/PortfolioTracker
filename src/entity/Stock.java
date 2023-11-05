package entity;

import java.time.LocalDateTime;

public class Stock {
    private String tickerSymbol;
    private LocalDateTime purchaseLocalDateTime;
    private double quantity;
    private double totalValueAtPurchase;

    public Stock(String tickerSymbol, LocalDateTime purchaseLocalDateTime, double quantity, double totalValueAtPurchase) {
        this.tickerSymbol = tickerSymbol;
        this.purchaseLocalDateTime = purchaseLocalDateTime;
        this.quantity = quantity;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalValueAtPurchase() {
        return totalValueAtPurchase;
    }

    public void setTotalValueAtPurchase(double totalValueAtPurchase) {
        this.totalValueAtPurchase = totalValueAtPurchase;
    }
}
