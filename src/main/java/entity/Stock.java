package entity;

import java.time.LocalDateTime;

public class Stock extends Investment{
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

    @Override
    public void setPurchaseLocalDateTime(LocalDateTime purchaseLocalDateTime) {
        this.purchaseLocalDateTime = purchaseLocalDateTime;
    }
    @Override
    public double getQuantity() {
        return quantity;
    }
    @Override

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    @Override

    public double getTotalValueAtPurchase() {
        return totalValueAtPurchase;
    }
    @Override

    public void setTotalValueAtPurchase(double totalValueAtPurchase) {
        this.totalValueAtPurchase = totalValueAtPurchase;
    }
}
