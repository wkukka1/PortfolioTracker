package entity.deserializer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import entity.AVTimeSeriesDailyResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.NoSuchElementException;

public class AVTimeSeriesDailyDeserializerImpl implements AVTimeSeriesDailyDeserializer {
    private static String OPEN_FIELD_NAME = "1. open";
    private static String HIGH_FIELD_NAME = "2. high";
    private static String LOW_FIELD_NAME = "3. low";
    private static String CLOSE_FIELD_NAME = "4. close";
    private static String VOLUME_FIELD_NAME = "5. volume";
    private static final String META_DATA_MARKER = "Meta Data";

    public AVTimeSeriesDailyResponse deserialize(String AVTimeSeriesDailyResStr, String date)
            throws IOException, IllegalArgumentException, NoSuchElementException {
        if (StringUtils.isEmpty(date)) {
            throw new IllegalArgumentException();
        } else if (!AVTimeSeriesDailyResStr.contains(META_DATA_MARKER)) {
            throw new IOException();
        } else if (!AVTimeSeriesDailyResStr.contains(date)) {
            throw new NoSuchElementException();
        }

        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(AVTimeSeriesDailyResStr);

        double open = -1;
        double high = -1;
        double low = -1;
        double close = -1;
        double volume = -1;

        while (!date.equals(jsonParser.getCurrentName())) {
            jsonParser.nextToken();
        }
        jsonParser.nextToken();

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jsonParser.getCurrentName();
            if (OPEN_FIELD_NAME.equals(fieldName)) {
                jsonParser.nextToken();
                open = Double.parseDouble(jsonParser.getText());
            }

            if (HIGH_FIELD_NAME.equals(fieldName)) {
                jsonParser.nextToken();
                high = Double.parseDouble(jsonParser.getText());
            }

            if (LOW_FIELD_NAME.equals(fieldName)) {
                jsonParser.nextToken();
                low = Double.parseDouble(jsonParser.getText());
            }

            if (CLOSE_FIELD_NAME.equals(fieldName)) {
                jsonParser.nextToken();
                close = Double.parseDouble(jsonParser.getText());
            }

            if (VOLUME_FIELD_NAME.equals(fieldName)) {
                jsonParser.nextToken();
                volume = Double.parseDouble(jsonParser.getText());
            }
        }

        jsonParser.close();
        return new AVTimeSeriesDailyResponse(date, open, high, low, close, volume);
    }
}
