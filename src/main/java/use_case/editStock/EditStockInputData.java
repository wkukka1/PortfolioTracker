package use_case.editStock;

public class EditStockInputData {
    private String tickerSymbol;
    private double quantity;

    private String username;
    public EditStockInputData(String tickerSymbol,
                              double newQuantity,
                              String username){
        this.tickerSymbol = tickerSymbol;
        this.quantity = newQuantity;
        this.username = username;
    }

    public String getTickerSymbol(){
        return this.tickerSymbol;
    }

    public double getNewQuantity(){
        return this.quantity;
    }

    public String getUsername(){return this.username;}
}
