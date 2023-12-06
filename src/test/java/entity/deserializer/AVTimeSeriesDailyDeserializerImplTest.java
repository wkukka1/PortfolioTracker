package entity.deserializer;

import entity.AVTimeSeriesDailyResponse;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class AVTimeSeriesDailyDeserializerImplTest {
    private AVTimeSeriesDailyDeserializer avTimeSeriesDailyDeserializer;
    private final String INVALID_JSON_STR = "abcdef";
    private final String VALID_JSON_STR = "{\n" +
            "    \"Meta Data\": {\n" +
            "        \"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\n" +
            "        \"2. Symbol\": \"SNOW\",\n" +
            "        \"3. Last Refreshed\": \"2023-12-01\",\n" +
            "        \"4. Output Size\": \"Full size\",\n" +
            "        \"5. Time Zone\": \"US/Eastern\"\n" +
            "    },\n" +
            "    \"Time Series (Daily)\": {\n" +
            "        \"2023-12-01\": {\n" +
            "            \"1. open\": \"185.2500\",\n" +
            "            \"2. high\": \"188.5600\",\n" +
            "            \"3. low\": \"180.8000\",\n" +
            "            \"4. close\": \"185.9700\",\n" +
            "            \"5. volume\": \"10315162\"\n" +
            "        }\n" +
            "}\n" +
            "}";

    @Before
    public void setup() {
        this.avTimeSeriesDailyDeserializer = new AVTimeSeriesDailyDeserializerImpl();
    }

    @Test
    public void testDeserializeOnValidJsonStr() throws Exception {
        AVTimeSeriesDailyResponse expectedAvResponse = new AVTimeSeriesDailyResponse("2023-12-01", 185.2500,
                188.5600, 180.8000, 185.9700, 10315162);
        AVTimeSeriesDailyResponse actualAvResponse = avTimeSeriesDailyDeserializer.deserialize(VALID_JSON_STR,
                "2023-12-01");

        assertEquals(expectedAvResponse.getClose(), actualAvResponse.getClose(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeThrowsIllegalArgumentExceptionForNullDate() throws Exception {
        avTimeSeriesDailyDeserializer.deserialize(VALID_JSON_STR, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeserializeThrowsIllegalArgumentExceptionForEmptyStrDate() throws Exception {
        avTimeSeriesDailyDeserializer.deserialize(VALID_JSON_STR, "");
    }

    @Test(expected = IOException.class)
    public void testDeserializeThrowsIOExceptionForInvalidJsonStr() throws Exception {
        avTimeSeriesDailyDeserializer.deserialize(INVALID_JSON_STR, "2023-12-01");
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeserializeThrowsNoSuchElemExceptionWhenDateNotFound() throws Exception {
        avTimeSeriesDailyDeserializer.deserialize(VALID_JSON_STR, "2023-11-30");
    }
}
