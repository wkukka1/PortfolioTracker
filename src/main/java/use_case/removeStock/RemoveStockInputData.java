package use_case.removeStock;

public class RemoveStockInputData {
    final private String ticker;

    private final String username;

    public RemoveStockInputData(String ticker, String username){
        this.ticker = ticker;
        this.username = username;
    }

    public String getTicker(){return this.ticker;}
    public String getUsername(){return this.username;}
}
