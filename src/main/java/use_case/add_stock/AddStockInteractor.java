package use_case.add_stock;

import entity.Portfolio;
import entity.Stock;
import use_case.show.StockPriceDataAccessInterface;
import use_case.signup.PortfolioDataAccessInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class AddStockInteractor implements AddStockInputBoundary {
    private final StockPriceDataAccessInterface stockPriceClientImpl;
    private final PortfolioDataAccessInterface portfolioDataAccessImpl;
    private final StockCalculationService stockCalculationServiceImpl;
    private final AddStockOutputBoundary addStockPresenter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String ADD_STOCK_DEFAULT_ERROR = "There was an issue adding this stock.\n Please check the input " +
            "fields and try again.";
    private final String ADD_STOCK_BAD_DATE_ERROR = "There is no stock data for this date.\n Please" +
            " check the input date and try again.\n Note: the markets are closed on weekends and holidays.";

    public AddStockInteractor(StockPriceDataAccessInterface stockPriceClientImpl,
                              PortfolioDataAccessInterface portfolioDataAccessImpl,
                              AddStockOutputBoundary addStockPresenter,
                              StockCalculationService stockCalculationServiceImpl) {
        this.stockPriceClientImpl = stockPriceClientImpl;
        this.portfolioDataAccessImpl = portfolioDataAccessImpl;
        this.addStockPresenter = addStockPresenter;
        this.stockCalculationServiceImpl = stockCalculationServiceImpl;
    }

    @Override
    public void addStock(AddStockInputData addStockData) {
        Stock newStock;
        double newStockProfitToDate;
        double overallNetProfit;
        try {
            newStock = createNewStock(addStockData);
            newStockProfitToDate = stockCalculationServiceImpl.calculateNewStockProfitToDate(newStock);
            overallNetProfit = portfolioDataAccessImpl.getPortfolioByID(addStockData.getUserID()).getNetProfit() +
                    newStockProfitToDate;
        } catch (IOException e) {
            addStockPresenter.prepareNonSuccessView(ADD_STOCK_DEFAULT_ERROR);
            return;
        } catch (IllegalArgumentException e) {
            addStockPresenter.prepareNonSuccessView(ADD_STOCK_BAD_DATE_ERROR);
            return;
        }

        portfolioDataAccessImpl.addStockToPortfolioByID(addStockData.getUserID(), newStock, newStockProfitToDate);
        Portfolio currPortfolio = portfolioDataAccessImpl.getPortfolioByID(addStockData.getUserID());
        Map<String, Double> tickersToQuantities = currPortfolio.generateTickersToQuantities();

        AddStockOutputData addStockOutputData = new AddStockOutputData(round(overallNetProfit, 2),
                tickersToQuantities);
        addStockPresenter.prepareSuccessView(addStockOutputData);
    }

    private Stock createNewStock(AddStockInputData addStockData) throws IOException, IllegalArgumentException {
        String purchaseDate = addStockData.getPurchaseLocalDateTime().format(formatter);

        double pastStockClosePrice = stockPriceClientImpl.getStockInfoByDate(
                addStockData.getTickerSymbol(), purchaseDate).getClose();

        double newStockQuantity = addStockData.getTotalValueAtPurchase() / pastStockClosePrice;
        return new Stock(addStockData.getTickerSymbol(), addStockData.getPurchaseLocalDateTime(), newStockQuantity,
                addStockData.getTotalValueAtPurchase());
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
