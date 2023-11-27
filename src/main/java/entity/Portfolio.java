package entity;

import java.util.List;

public class Portfolio {
    private List<Stock> stockList;
    private double netWorth;
    private int userID;

    public Portfolio(List<Stock> stockList, long netWorth, int userID) {
        this.stockList = stockList;
        this.netWorth = netWorth;
        this.userID = userID;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
