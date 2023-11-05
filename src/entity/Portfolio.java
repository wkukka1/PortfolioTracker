package entity;

import java.util.ArrayList;

public class Portfolio {
    private ArrayList<Stock> stockList;
    private long netWorth;
    private int userID;

    public Portfolio(ArrayList<Stock> stockList, long netWorth) {
        this.stockList = stockList;
        this.netWorth = netWorth;
    }

    public ArrayList<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(ArrayList<Stock> stockList) {
        this.stockList = stockList;
    }

    public long getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(long netWorth) {
        this.netWorth = netWorth;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
