package data_access;

import entity.Portfolio;
import entity.Stock;
import org.apache.commons.lang3.StringUtils;
import use_case.delete_user.DeletePortfolioAccessInterface;
import use_case.show.ShowPortfolioDataAccessInterface;
import use_case.signup.PortfolioDataAccessInterface;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class FilePortfolioDataAccessObject implements PortfolioDataAccessInterface, DeletePortfolioAccessInterface, ShowPortfolioDataAccessInterface {
    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<Integer, Portfolio> portfolios = new HashMap<>();

    private final String STOCK_ATTRIBUTE_DELIMITER = "-";
    private final String STOCK_ENTITY_DELIMITER = "_";

    public FilePortfolioDataAccessObject(String csvPath) throws IOException {
        this.csvFile = new File(csvPath);
        headers.put("userID", 0);
        headers.put("netProfit", 1);
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
                    double netProfit = Double.parseDouble(String.valueOf(col[headers.get("netProfit")]));

                    String encodedStocks = String.valueOf(col[headers.get("stockList")]);
                    ArrayList<Stock> decodedStockList = decodeStockStrIntoStockList(encodedStocks);

                    Portfolio currPortfolio = new Portfolio(decodedStockList, netProfit, userID);
                    portfolios.put(userID, currPortfolio);
                }
            }
        }
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

    @Override
    public void addStockToPortfolioByID(int userID, Stock newStock, double stockProfitToDate) {
        Portfolio currPortfolio = portfolios.get(userID);
        currPortfolio.addStockToStockList(newStock);
        currPortfolio.setNetProfit(currPortfolio.getNetProfit() + stockProfitToDate);

        this.save();
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
                        portfolio.getUserID(), portfolio.getNetProfit(), encodedStocks);

                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Takes a string of encoded stock information, where stock attributes tickerSymbol, totalValueAtPurchase,
     * quantity, and purchaseLocalDate are concatenated in the form
     *  {tickerSymbol1}-{totalValueAtPurchase1}-{quantity1}-{purchaseLocalDate1}_{tickerSymbol2}-{totalValueAtPurchase2}-...
     *  Ex.
     *     "AAPL-500.0-5.0-2023-11-05T12:17:52.780799_GOOG-600.0-6.0-2023-11-06T12:17:52.780799" and returns an ArrayList of
     *     Stock objects.
     *     <p>
     *         Here, "-" is the delimiter between 2 attributes of the same stock and "_" is the delimiter between 2
     *         unique stock objects. The attributes are extracted from the string based on the specification above. Note:
     *         the "-" symbols in the String representation of LocalDateTime objects are considered different to the "-" delimiter.
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
        if (StringUtils.isEmpty(encodedStocks)) {
            return stockList;
        }

        int prevStockDelimiterIndex = -1;
        int currStockDelimiterIndex = encodedStocks.indexOf("_");

        while (currStockDelimiterIndex != -1) {
            Stock newStock = decodeIndividualStockIntoStockObj(encodedStocks, prevStockDelimiterIndex, currStockDelimiterIndex);
            stockList.add(newStock);

            prevStockDelimiterIndex = currStockDelimiterIndex;
            currStockDelimiterIndex = encodedStocks.indexOf("_", prevStockDelimiterIndex + 1);
        }
        Stock newStock = decodeIndividualStockIntoStockObj(encodedStocks, prevStockDelimiterIndex, encodedStocks.length());
        stockList.add(newStock);

        return stockList;
    }

    private Stock decodeIndividualStockIntoStockObj(String encodedStocks, int startIndex,
                                              int endIndex) {
        int firstAttrDelimiterIndex = encodedStocks.indexOf("-", startIndex + 1);
        int secondAttrDelimiterIndex = encodedStocks.indexOf("-", firstAttrDelimiterIndex + 1);
        int thirdAttrDelimiterIndex = encodedStocks.indexOf("-", secondAttrDelimiterIndex + 1);

        String tickerSymbol = encodedStocks.substring(startIndex + 1, firstAttrDelimiterIndex);
        double stockValueAtPurchase = Double.parseDouble(encodedStocks.substring(firstAttrDelimiterIndex + 1,
                secondAttrDelimiterIndex));
        double stockQuantity = Double.parseDouble(encodedStocks.substring(secondAttrDelimiterIndex + 1,
                thirdAttrDelimiterIndex));
        LocalDateTime purchaseLocalDateTime = LocalDateTime.parse(
                encodedStocks.substring(thirdAttrDelimiterIndex + 1, endIndex));

        return new Stock(tickerSymbol, purchaseLocalDateTime, stockQuantity, stockValueAtPurchase);
    }

    /**
     * Takes an ArrayList of Stock objects and returns an encoded string where Stock attributes tickerSymbol,
     * totalValueAtPurchase, quantity, and purchaseLocalDate are concatenated in the form
     *  {tickerSymbol1}-{totalValueAtPurchase1}-{quantity1}-{purchaseLocalDate1}_{tickerSymbol2}-{totalValueAtPurchase2}-...
     *  Ex.
     *     "AAPL-500.0-5.0-2023-11-05T12:17:52.780799_GOOG-600.0-6.0-2023-11-06T12:17:52.780799"
     *     <p>
     *         Here, "-" is the delimiter between 2 attributes of the same stock and "_" is the delimiter between 2
     *         unique stock objects. Note: the "-" symbols in the String representation of LocalDateTime objects are
     *         considered different to the "-" delimiter.
     *     </p>
     *     <p>
     *         If the input ArrayList is empty, this method returns "".
     *     </p>
     *
     * @param stockList
     * @return encoded stock string
     */
    private String encodeStockListIntoStockStr(ArrayList<Stock> stockList) {
        String encodedStocks = "";
        for (int i = 0; i < stockList.size(); i++) {
            Stock currStock = stockList.get(i);
            if (i > 0) {
                encodedStocks = encodedStocks + STOCK_ENTITY_DELIMITER;
            }
            encodedStocks = encodedStocks + currStock.getTickerSymbol() + STOCK_ATTRIBUTE_DELIMITER +
                    currStock.getTotalValueAtPurchase() + STOCK_ATTRIBUTE_DELIMITER + currStock.getQuantity() +
                    STOCK_ATTRIBUTE_DELIMITER + currStock.getPurchaseLocalDateTime();
        }
        return encodedStocks;
    }

    @Override
    public void deletePortfolio(int id) {
        portfolios.remove(id);
        this.save();
    }
}
