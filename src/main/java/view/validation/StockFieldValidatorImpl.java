package view.validation;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StockFieldValidatorImpl implements StockFieldValidator {
    private final Integer EARLIEST_STOCK_YEAR = 1973;
    private final Integer NUM_MONTHS = 12;
    private final Integer MIN_MONTHS_OR_DAYS = 1;
    private Map<String, Integer> MONTHS_TO_DAYS = new HashMap<>();
    private final Pattern DOUBLE_REGEX_PATTERN = Pattern.compile("^\\d*\\.?\\d+|\\d+\\.?\\d*");
    private final Pattern ZERO_VALUE_PATTERN = Pattern.compile("[0]+\\.?[0]*|[0]*\\.?[0]+");

    public StockFieldValidatorImpl() {
        initMonthsToDays();
    }

    private void initMonthsToDays() {
        Map<String, Integer> months_to_days = new HashMap<>();
        months_to_days.put("01", 31);
        months_to_days.put("02", 28);
        months_to_days.put("03", 31);
        months_to_days.put("04", 30);
        months_to_days.put("05", 31);
        months_to_days.put("06", 30);
        months_to_days.put("07", 31);
        months_to_days.put("08", 31);
        months_to_days.put("09", 30);
        months_to_days.put("10", 31);
        months_to_days.put("11", 30);
        months_to_days.put("12", 31);
        this.MONTHS_TO_DAYS = months_to_days;
    }

    @Override
    public boolean isDateStrValid(String date) {
        String[] dateComponents = date.split("/");
        if (Integer.parseInt(dateComponents[1]) > NUM_MONTHS || Integer.parseInt(dateComponents[1]) <
                MIN_MONTHS_OR_DAYS) {
            return false;
        } if (Integer.parseInt(dateComponents[0]) > MONTHS_TO_DAYS.get(dateComponents[1]) ||
                Integer.parseInt(dateComponents[0]) < MIN_MONTHS_OR_DAYS) {
            return false;
        } if (Integer.parseInt(dateComponents[2]) > Year.now().getValue() ||
            Integer.parseInt(dateComponents[2]) < EARLIEST_STOCK_YEAR) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isTickerStrValid(String ticker) {
        return Pattern.matches("[a-zA-Z]+", ticker);
    }

    @Override
    public boolean isAmountStrValid(String amount) {
        if (!DOUBLE_REGEX_PATTERN.matcher(amount).matches()) {
            return false;
        }
        return !ZERO_VALUE_PATTERN.matcher(amount).matches();
    }
}
