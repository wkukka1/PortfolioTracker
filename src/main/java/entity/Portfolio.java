package entity;

import java.util.List;

public class Portfolio {
    private List<Stock> stockList;
    private double netProfit;
    private int userID;

    public Portfolio(List<Stock> stockList, double netProfit, int userID) {
        this.stockList = stockList;
        this.netProfit = netProfit;
        this.userID = userID;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public void addStockToStockList(Stock newStock) {
        stockList.add(newStock);
    }

    public double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
