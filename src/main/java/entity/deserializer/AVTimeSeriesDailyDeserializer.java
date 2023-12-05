package entity.deserializer;

import entity.AVTimeSeriesDailyResponse;

import java.io.IOException;
import java.util.NoSuchElementException;

public interface AVTimeSeriesDailyDeserializer {
     AVTimeSeriesDailyResponse deserialize(String AVTimeSeriesDailyResStr, String date) throws IOException,
            IllegalArgumentException, NoSuchElementException;
}
