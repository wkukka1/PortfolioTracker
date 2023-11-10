package interface_adapter.logged_in;

public class LoggedInState {
    private String username = "";
    private float netProfit = 0;

    public LoggedInState(LoggedInState copy) {
        username = copy.username;
        //TODO Update the netprofit
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public LoggedInState() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNetProfit() {
        return Float.toString(netProfit);
    }
}
