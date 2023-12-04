package entity;

public class AVTimeSeriesDailyResponse {
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;

    public AVTimeSeriesDailyResponse(String date, double open, double high, double low, double close, double volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public double getClose() {
        return close;
    }
}
