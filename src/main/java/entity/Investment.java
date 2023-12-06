package entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
public abstract class Investment {
    private LocalDateTime purchaseLocalDateTime;
    private double quantity;
    private double totalValueAtPurchase;
    abstract void setPurchaseLocalDateTime(LocalDateTime purchaseLocalDateTime);
    public abstract double getQuantity();
    public abstract LocalDateTime getPurchaseLocalDateTime();
    abstract void setQuantity(double quantity);
    public abstract double getTotalValueAtPurchase();
    abstract void setTotalValueAtPurchase(double totalValueAtPurchase);
    public abstract String getTickerSymbol();
}
