package use_case.show;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Portfolio;
import entity.Stock;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.json.JSONObject;
import org.jfree.data.time.TimeSeriesCollection;



public class ShowInteractor implements ShowInputBoundary{
    final ShowPortfolioDataAccessInterface portfolioDataAccessObject;
    final StockPriceDataAccessInterface stockDataAccessObject;
    final ShowOutputBoundary showPresenter;

    public ShowInteractor(ShowPortfolioDataAccessInterface portfolioDataAccessObject, StockPriceDataAccessInterface stockDataAccessObject, ShowOutputBoundary showPresenter) {
        this.portfolioDataAccessObject = portfolioDataAccessObject;
        this.stockDataAccessObject = stockDataAccessObject;
        this.showPresenter = showPresenter;
    }


    @Override
    public void execute(ShowInputData showInputData) throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        Portfolio portfolio = portfolioDataAccessObject.getPortfolioByID(showInputData.getUserID());
        List<Stock> stockList = portfolio.getStockList();

        TimeSeriesCollection dataset = new TimeSeriesCollection(); // For net worth plot
        TimeSeries series = new TimeSeries("Net Worth"); // For net worth plot
        // Key in YYYY-MM-DD format, value is total net worth of portfolio on that day
        HashMap<String, Double> dateToNetWorth = new HashMap<>();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDate = today.minusDays(360);

        for (Stock stock : stockList) {
            // Going through each stock in the list of stocks, making an API call for each one
            JSONObject rawStockInfo = stockDataAccessObject.getStockInfo(stock.getTickerSymbol());
            HashMap<String, HashMap<String, String>> processedStockInfo = jsonToHashMap(rawStockInfo);
            LocalDateTime purchaseDate = stock.getPurchaseLocalDateTime();
            for (LocalDateTime date = startDate; date.isBefore(today); date = date.plusDays(1)) {
                String dateStringWithoutTime = date.toString().substring(0, 10);
                System.out.println(dateStringWithoutTime); // todo delete
                System.out.println(processedStockInfo); // todo delete
                if (date.compareTo(purchaseDate) >= 0) {
                    HashMap<String, String> dailyData = processedStockInfo.get(dateStringWithoutTime);
                    System.out.println(dailyData); // todo delete
                    if (dailyData != null) {
                        String closingPrice = dailyData.get("4. close");
                        Double price = Double.valueOf(closingPrice);
                        dateToNetWorth.put(dateStringWithoutTime,
                                dateToNetWorth.getOrDefault(dateStringWithoutTime, 0.0) + stock.getQuantity() * price);
                    }
                    else {
                        dateToNetWorth.put(dateStringWithoutTime,
                                dateToNetWorth.getOrDefault(dateStringWithoutTime, 0.0));
                    }
                }
                else {
                    dateToNetWorth.put(dateStringWithoutTime,
                            dateToNetWorth.getOrDefault(dateStringWithoutTime, 0.0));
                }
            };
        }

        System.out.println("HERE");
        System.out.println(dateToNetWorth); //todo dlete
        for (Map.Entry<String, Double> entry : dateToNetWorth.entrySet()) {
            Day dayObject = stringToDay(entry.getKey());
            series.add(dayObject, entry.getValue());
        }

        dataset.addSeries(series);
        ChartPanel panel = getPlot(dataset);
//        double newNetWorth = 0.0; //todo update
//        double netProfit = 0.0; //todo update

//        portfolio.setNetWorth(newNetWorth); // Updating the portfolio's net worth
        if (panel != null) {
            System.out.println("THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG");
        }  // todo dlete later above
        ShowOutputData showOutputData = new ShowOutputData(panel);
        showPresenter.prepareSuccessView(showOutputData);
    }

    public HashMap<String, HashMap<String, String>> jsonToHashMap(JSONObject rawStockInfo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap rawMap = mapper.readValue(rawStockInfo.toString(), HashMap.class);
        return (HashMap<String, HashMap<String, String>>) rawMap.get("Time Series (Daily)");

    }

    /**
     * Converts a string in the format YYYY-MM-DD to a Day object
     * Reference: https://www.jfree.org/jfreechart/api/javadoc/org/jfree/data/time/Day.html
     *
     * @param stringDate a string in the format YYYY-MM-DD
     * @return the corresponding Day object
     */
    private Day stringToDay(String stringDate) {
        String[] tokens = stringDate.split("-");
        return new Day(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[0]));
    }

    /**
     * Updates the hashmap mapping dates to net worth with information from the API call.
     *
     * @param dateToNetWorth the hashmap to be updated
     * @param stockInfo      the hashmap containing information from the API call
     * @param stockQuantity  the quantity of the stock held
     */
//    private void updateDateToNetWorth(HashMap<String, Double> dateToNetWorth, HashMap<String, Double> stockInfo, double stockQuantity) {
//        for (Map.Entry<String, Double> entry : stockInfo.entrySet()) {
//            String date = entry.getKey();
//            dateToNetWorth.put(date, dateToNetWorth.get(date) + entry.getValue() * stockQuantity);
//        }
//    }


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
