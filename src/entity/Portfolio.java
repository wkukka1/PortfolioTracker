package entity;

import java.util.List;

public class Portfolio {
    private List<Stock> stockList;
    private long netWorth;
    private int userID;

    public Portfolio(List<Stock> stockList, long netWorth) {
        this.stockList = stockList;
        this.netWorth = netWorth;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
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
