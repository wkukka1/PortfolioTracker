package use_case.login;

public class LoginOutputData {

    private final String username;
    private final int userID;
    private boolean useCaseFailed;
    private double netProfit;

    public LoginOutputData(String username, int userID, boolean useCaseFailed, double netProfit) {
        this.username = username;
        this.userID = userID;
        this.useCaseFailed = useCaseFailed;
        this.netProfit = netProfit;
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
}
