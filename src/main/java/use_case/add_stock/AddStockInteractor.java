package use_case.add_stock;

import entity.Stock;
import use_case.show.StockPriceDataAccessInterface;
import use_case.signup.PortfolioDataAccessInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddStockInteractor implements AddStockInputBoundary {
    private final StockPriceDataAccessInterface stockPriceClientImpl;
    private final PortfolioDataAccessInterface portfolioDataAccessImpl;
    private final AddStockOutputBoundary addStockPresenter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String ADD_STOCK_DEFAULT_ERROR = "There was an issue adding this stock. Please check the input " +
            "fields and try again.";

    public AddStockInteractor(StockPriceDataAccessInterface stockPriceClientImpl,
                              PortfolioDataAccessInterface portfolioDataAccessImpl,
                              AddStockOutputBoundary addStockPresenter) {
        this.stockPriceClientImpl = stockPriceClientImpl;
        this.portfolioDataAccessImpl = portfolioDataAccessImpl;
        this.addStockPresenter = addStockPresenter;
    }

    @Override
    public void addStock(AddStockInputData addStockData) {
        Stock newStock;
        double newStockProfitToDate;
        double overallNetProfit;
        try {
            newStock = createNewStock(addStockData);
            newStockProfitToDate = calculateNewStockProfitToDate(newStock);
            overallNetProfit = portfolioDataAccessImpl.getPortfolioByID(addStockData.getUserID()).getNetProfit() +
                    newStockProfitToDate;
        } catch (RuntimeException re) {
            addStockPresenter.prepareNonSuccessView(ADD_STOCK_DEFAULT_ERROR);
            return;
        }

        portfolioDataAccessImpl.addStockToPortfolioByID(addStockData.getUserID(), newStock, newStockProfitToDate);
        addStockPresenter.prepareSuccessView(round(overallNetProfit, 2));
    }

    private Stock createNewStock(AddStockInputData addStockData) throws RuntimeException {
        String purchaseDate = addStockData.getPurchaseLocalDateTime().format(formatter);

        double pastStockClosePrice = stockPriceClientImpl.getStockInfoByDate(
                addStockData.getTickerSymbol(), purchaseDate).getClose();

        double newStockQuantity = addStockData.getTotalValueAtPurchase() / pastStockClosePrice;
        return new Stock(addStockData.getTickerSymbol(), addStockData.getPurchaseLocalDateTime(), newStockQuantity,
                addStockData.getTotalValueAtPurchase());
    }

    private double calculateNewStockProfitToDate(Stock newStock) throws RuntimeException {
        String currDate = LocalDateTime.now().minusDays(1).format(formatter);

        double currStockClosePrice = stockPriceClientImpl.getStockInfoByDate(
                newStock.getTickerSymbol(), currDate).getClose();

        double pastStockClosePrice = newStock.getTotalValueAtPurchase() / newStock.getQuantity();
        return newStock.getQuantity() * (currStockClosePrice - pastStockClosePrice);
    }

    private double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
