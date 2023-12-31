package entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private List<Investment> stockList;
    private double netProfit;
    private int userID;

    public Portfolio(List<Investment> stockList, double netProfit, int userID) {
        this.stockList = stockList;
        this.netProfit = netProfit;
        this.userID = userID;
    }

    public List<Investment> getStockList() {
        return stockList;
    }

    public void setStockList(List<Investment> stockList) {
        this.stockList = stockList;
    }


    public void addStockToStockList(Investment newStock) {
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

    public Investment getStockByTicker(String tickerSymbol) {
        for (Investment s : stockList) {
            String ticker = s.getTickerSymbol();
            if (ticker.equals(tickerSymbol)) {
                return s;
            }
        }
        System.out.println("No stock found");
        return null;
    }
    public Map<String, Double> generateTickersToQuantities() {
        Map<String, Double> tickersToQuantities = new HashMap<>();
        for (Investment stock : this.getStockList()) {
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
