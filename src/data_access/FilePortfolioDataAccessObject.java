package data_access;

import entity.Portfolio;
import entity.Stock;
import use_case.signup.PortfolioDataAccessInterface;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class FilePortfolioDataAccessObject implements PortfolioDataAccessInterface {
    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<Integer, Portfolio> portfolios = new HashMap<>();

    private final String STOCKATTRIBUTEDELIMITER = "-";
    private final String STOCKDELIMITER = "_";

    public FilePortfolioDataAccessObject(String csvPath) throws IOException {
        this.csvFile = new File(csvPath);
        headers.put("userID", 0);
        headers.put("netWorth", 1);
        headers.put("stockList", 2);

        if (csvFile.length() == 0) {
            save();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String row;
                reader.readLine();
                while ((row = reader.readLine()) != null) {
                    String[] col = row.split(",", headers.size());

                    int userID = Integer.parseInt(String.valueOf(col[headers.get("userID")]));
                    long netWorth = Long.parseLong(String.valueOf(col[headers.get("netWorth")]));

                    String encodedStocks = String.valueOf(col[headers.get("stockList")]);
                    ArrayList<Stock> decodedStockList = decodeStockStrIntoStockList(encodedStocks);

                    Portfolio currPortfolio = new Portfolio(decodedStockList, netWorth, userID);
                    portfolios.put(userID, currPortfolio);
                }
            }
        }
    }

    private void save() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (Portfolio portfolio : portfolios.values()) {
                String encodedStocks = encodeStockListIntoStockStr((ArrayList<Stock>) portfolio.getStockList());

                String line = String.format("%s,%s,%s",
                        portfolio.getUserID(), portfolio.getNetWorth(), encodedStocks);

                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes a string of encoded stock information, where stock attributes like tickerSymbol, totalValueAtPurchase,
     * quantity, purchaseLocalDate are concatenated in the form
     *     "AAPL-500-5-2023-11-05T12:17:52.780799_GOOG-600-6-2023-11-06T12:17:52.780799" and returns an ArrayList of
     *     Stock objects.
     *     <p>
     *         Here, "-" is the delimiter between 2 attributes of the same stock and "_" is the delimiter between 2
     *         unique stock objects. The attributes are extracted from the string based on the specification above.
     *     </p>
     *     <p>
     *         If the input String is empty, this method returns an empty ArrayList.
     *     </p>
     *
     * @param encodedStocks
     * @return ArrayList<Stock>
     */
    private ArrayList<Stock> decodeStockStrIntoStockList(String encodedStocks) {
        ArrayList<Stock> stockList = new ArrayList<>();
        if ("".equals(encodedStocks)) {
            return stockList;
        }

        int lastStockDelimiterIndex = -1;
        int indexOfStockDelimiter = encodedStocks.indexOf("_");

        while (indexOfStockDelimiter != -1) {
            Stock newStock = decodeIndividualStockIntoStockObj(encodedStocks, lastStockDelimiterIndex, indexOfStockDelimiter);
            stockList.add(newStock);

            lastStockDelimiterIndex = indexOfStockDelimiter;
            indexOfStockDelimiter = encodedStocks.indexOf("_", lastStockDelimiterIndex + 1);
        }
        Stock newStock = decodeIndividualStockIntoStockObj(encodedStocks, lastStockDelimiterIndex, encodedStocks.length());
        stockList.add(newStock);

        return stockList;
    }

    private Stock decodeIndividualStockIntoStockObj(String encodedStocks, int initialIndexOfStockDelimiter,
                                              int endIndexOfStockDelimiter) {
        int firstAttributeDelimiterIndex = encodedStocks.indexOf("-", initialIndexOfStockDelimiter + 1);
        int secondAttributeDelimiterIndex = encodedStocks.indexOf("-", firstAttributeDelimiterIndex + 1);
        int thirdAttributeDelimiterIndex = encodedStocks.indexOf("-", secondAttributeDelimiterIndex + 1);

        String tickerSymbol = encodedStocks.substring(initialIndexOfStockDelimiter + 1, firstAttributeDelimiterIndex);
        double stockValueAtPurchase = Double.parseDouble(encodedStocks.substring(firstAttributeDelimiterIndex + 1,
                secondAttributeDelimiterIndex));
        double stockQuantity = Double.parseDouble(encodedStocks.substring(secondAttributeDelimiterIndex + 1,
                thirdAttributeDelimiterIndex));
        LocalDateTime purchaseLocalDateTime = LocalDateTime.parse(
                encodedStocks.substring(thirdAttributeDelimiterIndex + 1, endIndexOfStockDelimiter));

        return new Stock(tickerSymbol, purchaseLocalDateTime, stockQuantity, stockValueAtPurchase);
    }

    /**
     * Takes an ArrayList of Stock objects and returns a string of encoded stock information, where stock attributes
     *     like tickerSymbol, totalValueAtPurchase, quantity, purchaseLocalDate are concatenated in the form
     *     "AAPL-500-5-2023-11-05T12:17:52.780799_GOOG-600-6-2023-11-06T12:17:52.780799".
     *     <p>
     *         Here, "-" is the delimiter between 2 attributes of the same stock and "_" is the delimiter between 2
     *         unique stock objects.
     *     </p>
     *     <p>
     *         If the input ArrayList is empty, this method returns "".
     *     </p>
     *
     * @param stockList
     * @return
     */
    private String encodeStockListIntoStockStr(ArrayList<Stock> stockList) {
        String encodedStocks = "";
        for (int i = 0; i < stockList.size(); i++) {
            Stock currStock = stockList.get(i);
            if (i > 0) {
                encodedStocks = encodedStocks + STOCKDELIMITER;
            }
            encodedStocks = encodedStocks + currStock.getTickerSymbol() + STOCKATTRIBUTEDELIMITER +
                    currStock.getTotalValueAtPurchase() + STOCKATTRIBUTEDELIMITER + currStock.getQuantity() +
                    STOCKATTRIBUTEDELIMITER + currStock.getPurchaseLocalDate();
        }
        return encodedStocks;
    }

    @Override
    public void savePortfolio(Portfolio currPortfolio) {
        portfolios.put(currPortfolio.getUserID(), currPortfolio);
        this.save();
    }

    @Override
    public Portfolio getPortfolioByID(int userID) {
        return this.portfolios.get(userID);
    }
}
