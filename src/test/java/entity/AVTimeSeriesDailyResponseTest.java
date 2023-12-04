package entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AVTimeSeriesDailyResponseTest {
    private AVTimeSeriesDailyResponse avResponse;

    @Before
    public void setup() {
        this.avResponse = new AVTimeSeriesDailyResponse("2023-01-01", 1, 1, 1, 42.0,
                1);
    }

    @Test
    public void getClose() {
        assertEquals(42.0, avResponse.getClose(), 0);
    }
}
