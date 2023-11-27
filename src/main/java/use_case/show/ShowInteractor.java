package use_case.show;


import entity.Portfolio;
import entity.Stock;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.json.JSONObject;
import org.jfree.data.time.TimeSeriesCollection;



public class ShowInteractor implements ShowInputBoundary{
    final PortfolioDataAccessInterface portfolioDataAccessObject;
    final StockPriceDataAccessInterface stockDataAccessObject;
    final ShowOutputBoundary showPresenter;

    public ShowInteractor(PortfolioDataAccessInterface portfolioDataAccessObject, StockPriceDataAccessInterface stockDataAccessObject, ShowOutputBoundary showPresenter) {
        this.portfolioDataAccessObject = portfolioDataAccessObject;
        this.stockDataAccessObject = stockDataAccessObject;
        this.showPresenter = showPresenter;
    }


    @Override
    public void execute(ShowInputData showInputData) {
        LocalDateTime now = LocalDateTime.now();
        Portfolio portfolio = portfolioDataAccessObject.getPortfolioByID(showInputData.getUserID());
        List<Stock> stockList = portfolio.getStockList();

        TimeSeriesCollection dataset = new TimeSeriesCollection(); // For net worth plot
        TimeSeries series = new TimeSeries("Net Worth"); // For net worth plot
        // Key in YYYY-MM-DD format, value is total net worth of portfolio on that day
        HashMap<String, Float> dateToNetWorth = new HashMap<>();
        LocalDate portfolioCreationDate = ...;
        LocalDate today = LocalDate.now();

        populateHashMap(dateToNetWorth, portfolioCreationDate, today);

        for (Stock stock : stockList) {
            // Going through each stock in the list of stocks, making an API call for each one
            JSONObject stockInfo = stockDataAccessObject.getStockInfo(stock.getTickerSymbol(), );
            updateDateToNetWorth(dateToNetWorth, stockInfo);
        }

        for (Map.Entry<String, Float> entry : dateToNetWorth.entrySet()) {
            Day dayObject = yyyymmddToDay(entry.getKey());
            series.add(dayObject, entry.getValue());
        }

        dataset.addSeries(series);
        ChartPanel panel = getPlot(dataset);
        double newNetWorth = ...;

        portfolio.setNetWorth(newNetWorth); // Updating the portfolio's net worth
        ShowOutputData showOutputData = new ShowOutputData(panel, newNetWorth);
        showPresenter.prepareSuccessView(showOutputData);
    }

    /**
     * Populates the hashmap mapping dates to prices with, with all values being 0.0 and keys being
     * in the range from portfolioCreationDate to today.
     *
     * @param dateToNetWorth the hashmap
     * @param portfolioCreationDate start date (when portfolio was created)
     * @param today end date (today)
     */
    private void populateHashMap(HashMap<String, Float> dateToNetWorth, LocalDate portfolioCreationDate, LocalDate today) {
        // todo: implement
    }

    /**
     * Converts a string in the format YYYY-MM-DD to a Day object
     *
     * @param stringDate a string in the format YYYY-MM-DD
     * @return the corresponding Day object
     */
    private Day yyyymmddToDay(String stringDate) {
        // todo: implement
    }

    /**
     * Updates the hashmap mapping dates to networth with information from the API call.
     *
     * @param dateToNetWorth the hashmap to be updated
     * @param stockInfo the JSON object containing information from the API call
     */
    private void updateDateToNetWorth(HashMap<String, Float> dateToNetWorth, JSONObject stockInfo) {
        // todo: implement
    }


    /**
     * Returns a ChartPanel object containing the plot of the net worth of the portfolio
     *
     * @param dataset the TimeSeriesCollection object containing information of the plot
     */
    private ChartPanel getPlot(TimeSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Net Worth Plot", // Chart
                "Date", // X-Axis Label
                "Net Worth ($)", // Y-Axis Label
                dataset);

        // Setting background colour
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255,255,255));
        return new ChartPanel(chart);
    }

}
