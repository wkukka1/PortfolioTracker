package use_case.add_stock;

import java.time.LocalDateTime;

public class AddStockInputData {
    private String tickerSymbol;
    private LocalDateTime purchaseLocalDateTime;
    private double totalValueAtPurchase;
    private int userID;

    private String investmentType;

    public AddStockInputData(String tickerSymbol, LocalDateTime purchaseLocalDateTime, double totalValueAtPurchase,
                             int userID, String investmentType) {
        this.tickerSymbol = tickerSymbol;
        this.purchaseLocalDateTime = purchaseLocalDateTime;
        this.totalValueAtPurchase = totalValueAtPurchase;
        this.userID = userID;
        this.investmentType = investmentType;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setInvestmentType(String investmentType){
        this.investmentType = investmentType;
    }

    public String getInvestmentType(){
        return investmentType;
    }
}
