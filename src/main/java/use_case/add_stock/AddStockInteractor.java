package use_case.add_stock;

import entity.Stock;
import use_case.signup.PortfolioDataAccessInterface;

public class AddStockInteractor implements AddStockInputBoundary {
    private final PortfolioDataAccessInterface portfolioDataAccessImpl;

    public AddStockInteractor(PortfolioDataAccessInterface portfolioDataAccessImpl) {
        this.portfolioDataAccessImpl = portfolioDataAccessImpl;
    }

    @Override
    public void addStock(AddStockInputData addStockData) {
        Stock newStock;
        double newStockProfitToDate;
        try {
            newStock = createNewStock(addStockData);
            newStockProfitToDate = calculateNewStockProfitToDate(newStock);
        } catch (RuntimeException re) {
            addStockPresenter.prepareNonSuccessView();
        }

        portfolioDataAccessImpl.addStockToPortfolioByID(addStockData.getUserID(), newStock, newStockProfitToDate);

        addStockPresenter.prepareSuccessView();
    }
}
