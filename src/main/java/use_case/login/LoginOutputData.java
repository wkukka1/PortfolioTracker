package use_case.login;

import java.util.Map;

public class LoginOutputData {

    private final String username;
    private final int userID;
    private boolean useCaseFailed;
    private double netProfit;
    private Map<String, Double> tickersToQuantities;

    public LoginOutputData(String username, int userID, boolean useCaseFailed, double netProfit,
                           Map<String, Double> tickersToQuantities) {
        this.username = username;
        this.userID = userID;
        this.useCaseFailed = useCaseFailed;
        this.netProfit = netProfit;
        this.tickersToQuantities = tickersToQuantities;
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(double netProfit) {
        this.netProfit = netProfit;
    }

    public Map<String, Double> getTickersToQuantities() {
        return tickersToQuantities;
    }

    public void setTickersToQuantities(Map<String, Double> tickersToQuantities) {
        this.tickersToQuantities = tickersToQuantities;
    }

}
