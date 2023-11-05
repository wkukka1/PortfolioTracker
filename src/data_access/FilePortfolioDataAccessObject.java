package data_access;

import entity.Portfolio;
import entity.Stock;
import use_case.signup.PortfolioDataAccessInterface;

import java.io.*;
import java.util.*;

public class FilePortfolioDataAccessObject implements PortfolioDataAccessInterface {
    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<Integer, Portfolio> portfolios = new HashMap<>();

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
}
