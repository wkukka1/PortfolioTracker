package use_case.add_stock;

import entity.Short;
import entity.Stock;
import use_case.show.StockPriceDataAccessInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockCalculationServiceImpl implements StockCalculationService {
    private final StockPriceDataAccessInterface stockPriceClientImpl;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final int MONDAY_DAY_OF_WEEK = 1;
    private final int SATURDAY_DAY_OF_WEEK = 6;
    private final int SUNDAY_DAY_OF_WEEK = 7;

    public StockCalculationServiceImpl(StockPriceDataAccessInterface stockPriceClientImpl) {
        this.stockPriceClientImpl = stockPriceClientImpl;
    }

    public double calculateNewStockProfitToDate(Stock newStock) throws IOException, IllegalArgumentException {
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

    @Override
    public double calculateNewShortProfitToDate(Short newShort) throws IOException {
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
                newShort.getTickerSymbol(), mostRecentStockDate).getClose();

        double pastStockClosePrice = newShort.getTotalValueAtPurchase() / newShort.getQuantity();
        return newShort.getQuantity() * (pastStockClosePrice - currStockClosePrice);
    }
}
