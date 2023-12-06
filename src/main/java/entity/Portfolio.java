package entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Double> generateTickersToQuantities() {
        Map<String, Double> tickersToQuantities = new HashMap<>();
        for (Stock stock : this.getStockList()) {
            tickersToQuantities.put(stock.getTickerSymbol(), tickersToQuantities.getOrDefault(stock.getTickerSymbol(),
                    0.0) + stock.getQuantity());
        }
        tickersToQuantities.replaceAll((ticker, quantity) -> roundToHundredths(quantity));
        return tickersToQuantities;
    }

    private double roundToHundredths(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
