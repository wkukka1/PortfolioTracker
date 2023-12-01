package use_case.editStock;

public interface EditStockOutputBoundary {
    void prepareSuccessView();
    void prepareFailView(String error);
}
