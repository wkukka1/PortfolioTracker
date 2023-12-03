package use_case.add_stock;

import entity.Portfolio;
import entity.Stock;
import use_case.show.StockPriceDataAccessInterface;
import use_case.signup.PortfolioDataAccessInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class AddStockInteractor implements AddStockInputBoundary {
    private final StockPriceDataAccessInterface stockPriceClientImpl;
    private final PortfolioDataAccessInterface portfolioDataAccessImpl;
    private final AddStockOutputBoundary addStockPresenter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final String ADD_STOCK_DEFAULT_ERROR = "There was an issue adding this stock.\n Please check the input " +
            "fields and try again.";
    private final String ADD_STOCK_BAD_DATE_ERROR = "There is no stock data for this date.\n Please" +
            " check the input date and try again.\n Note: the markets are closed on weekends and holidays.";
    private final int MONDAY_DAY_OF_WEEK = 1;
    private final int SATURDAY_DAY_OF_WEEK = 6;
    private final int SUNDAY_DAY_OF_WEEK = 7;

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
        } catch (IOException e) {
            addStockPresenter.prepareNonSuccessView(ADD_STOCK_DEFAULT_ERROR);
            return;
        } catch (IllegalArgumentException e) {
            addStockPresenter.prepareNonSuccessView(ADD_STOCK_BAD_DATE_ERROR);
            return;
        }

        portfolioDataAccessImpl.addStockToPortfolioByID(addStockData.getUserID(), newStock, newStockProfitToDate);
        Map<String, Double> tickersToQuantities = generateTickersToQuantities(
                portfolioDataAccessImpl.getPortfolioByID(addStockData.getUserID()));

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

    private double calculateNewStockProfitToDate(Stock newStock) throws IOException, IllegalArgumentException {
        int todayDayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        String mostRecentStockDate;
        if (todayDayOfWeek == SUNDAY_DAY_OF_WEEK || todayDayOfWeek == SATURDAY_DAY_OF_WEEK) {
            mostRecentStockDate = LocalDateTime.now().minusDays((todayDayOfWeek % 2) + 1).format(formatter);
        } else if (todayDayOfWeek == MONDAY_DAY_OF_WEEK) {
            mostRecentStockDate = LocalDateTime.now().minusDays(3).format(formatter);
        } else {
            mostRecentStockDate = LocalDateTime.now().minusDays(1).format(formatter);
        }

        double currStockClosePrice = stockPriceClientImpl.getStockInfoByDate(
                newStock.getTickerSymbol(), mostRecentStockDate).getClose();

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

    private Map<String, Double> generateTickersToQuantities(Portfolio currPortfolio) {
        Map<String, Double> tickersToQuantities = new HashMap<>();
        for (Stock stock : currPortfolio.getStockList()) {
            tickersToQuantities.put(stock.getTickerSymbol(), tickersToQuantities.getOrDefault(stock.getTickerSymbol(),
                    0.0) + stock.getQuantity());
        }
        tickersToQuantities.replaceAll((ticker, quantity) -> round(quantity, 2));
        return tickersToQuantities;
    }
}
