package use_case.add_stock;

import entity.Stock;
import use_case.show.StockPriceDataAccessInterface;
import use_case.signup.PortfolioDataAccessInterface;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddStockInteractor implements AddStockInputBoundary {
    private final StockPriceDataAccessInterface stockPriceClientImpl;
    private final PortfolioDataAccessInterface portfolioDataAccessImpl;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AddStockInteractor(StockPriceDataAccessInterface stockPriceClientImpl,
                              PortfolioDataAccessInterface portfolioDataAccessImpl) {
        this.stockPriceClientImpl = stockPriceClientImpl;
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

    private Stock createNewStock(AddStockInputData addStockData) throws RuntimeException {
        String purchaseDate = addStockData.getPurchaseLocalDateTime().format(formatter);

        double pastStockClosePrice = stockPriceClientImpl.getStockInfo(
                addStockData.getTickerSymbol(), purchaseDate).getClose();

        double newStockQuantity = addStockData.getTotalValueAtPurchase() / pastStockClosePrice;
        return new Stock(addStockData.getTickerSymbol(), addStockData.getPurchaseLocalDateTime(), newStockQuantity,
                addStockData.getTotalValueAtPurchase());
    }

    private double calculateNewStockProfitToDate(Stock newStock) throws RuntimeException {
        String currDate = LocalDateTime.now().format(formatter);

        double currStockClosePrice = stockPriceClientImpl.getStockInfo(
                newStock.getTickerSymbol(), currDate).getClose();

        double pastStockClosePrice = newStock.getTotalValueAtPurchase() / newStock.getQuantity();
        return newStock.getQuantity() * (currStockClosePrice - pastStockClosePrice);
    }
}
