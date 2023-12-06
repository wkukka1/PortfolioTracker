package entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
public abstract class Investment {
    private LocalDateTime purchaseLocalDateTime;
    private double quantity;
    private double totalValueAtPurchase;
    abstract void setPurchaseLocalDateTime(LocalDateTime purchaseLocalDateTime);
    abstract double getQuantity();
    abstract LocalDateTime getPurchaseLocalDateTime();
    abstract void setQuantity(double quantity);
    abstract double getTotalValueAtPurchase();
    abstract void setTotalValueAtPurchase(double totalValueAtPurchase);
}
