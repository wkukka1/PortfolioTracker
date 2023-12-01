package use_case.editStock;

public class EditStockInputData {
    private String tickerSymbol;
    private double quantity;
    public EditStockInputData(String tickerSymbol,
                              double newQuantity){
        this.tickerSymbol = tickerSymbol;
        this.quantity = newQuantity;
    }

    public String getTickerSymbol(){
        return this.tickerSymbol;
    }

    public double getNewQuantity(){
        return this.quantity;
    }
}
