package use_case.editStock;

public interface EditStockOutputBoundary {
    void prepareSuccessView(EditStockOutputData output);
    void prepareFailView(String error);
}
