package use_case.add_stock;

import java.time.LocalDateTime;

public class AddStockInputData {
    private String tickerSymbol;
    private LocalDateTime purchaseLocalDateTime;
    private double totalValueAtPurchase;

    public AddStockInputData(String tickerSymbol, LocalDateTime purchaseLocalDateTime, double totalValueAtPurchase) {
        this.tickerSymbol = tickerSymbol;
        this.purchaseLocalDateTime = purchaseLocalDateTime;
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

    public double getTotalValueAtPurchase() {
        return totalValueAtPurchase;
    }

    public void setTotalValueAtPurchase(double totalValueAtPurchase) {
        this.totalValueAtPurchase = totalValueAtPurchase;
    }
}
