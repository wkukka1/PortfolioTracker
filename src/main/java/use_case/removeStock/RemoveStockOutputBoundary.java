package use_case.removeStock;

import use_case.removeStock.RemoveStockOutputData;
public interface RemoveStockOutputBoundary {
    void prepareSuccessView(RemoveStockOutputData outputData);
    void prepareFailView(String error);
}
