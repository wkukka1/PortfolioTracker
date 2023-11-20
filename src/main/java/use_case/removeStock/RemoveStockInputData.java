package use_case.removeStock;

public class RemoveStockInputData {
    final private String tickerNumber;

    public RemoveStockInputData(String tickerNumber){
        this.tickerNumber = tickerNumber;
    }

    String getTickerNumber(){return tickerNumber;}
}
