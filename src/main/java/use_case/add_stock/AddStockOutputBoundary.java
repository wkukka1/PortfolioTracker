package use_case.add_stock;

import java.util.Map;

public interface AddStockOutputBoundary {
    void prepareSuccessView(AddStockOutputData addStockOutputData);
    void prepareNonSuccessView(String addStockErrorMsg);
}
