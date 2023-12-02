package use_case.add_stock;

public interface AddStockOutputBoundary {
    void prepareSuccessView(double newStockProfitToDate);
    void prepareNonSuccessView(String addStockErrorMsg);
}
