package entity;

import java.time.LocalDateTime;

public class Stock {
    private String tickerSymbol;
    private LocalDateTime purchaseLocalDate;
    private double quantity;
    private double totalValueAtPurchase;

    public Stock(String tickerSymbol, LocalDateTime purchaseLocalDate, double quantity, double totalValueAtPurchase) {
        this.tickerSymbol = tickerSymbol;
        this.purchaseLocalDate = purchaseLocalDate;
        this.quantity = quantity;
        this.totalValueAtPurchase = totalValueAtPurchase;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public LocalDateTime getPurchaseLocalDate() {
        return purchaseLocalDate;
    }

    public void setPurchaseLocalDate(LocalDateTime purchaseLocalDate) {
        this.purchaseLocalDate = purchaseLocalDate;
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
