package entity;

import java.util.List;

public class Portfolio {
    private List<Stock> stockList;
    private long netWorth;
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

    public Stock getStockByTicker(String tickerSymbol){
        for (Stock s: stockList){
            String ticker = s.getTickerSymbol();
            if (ticker.equals(tickerSymbol)){
                return s;
            }
        }
        System.out.println("No stock found");
        return null;
    }
}
