package use_case.add_stock;

public interface AddStockOutputBoundary {
    void prepareSuccessView(AddStockOutputData addStockOutputData);
    void prepareNonSuccessView(String addStockErrorMsg);
}
