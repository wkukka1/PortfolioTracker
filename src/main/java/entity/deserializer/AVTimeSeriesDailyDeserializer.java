package entity.deserializer;

import entity.AVTimeSeriesDailyResponse;

import java.io.IOException;

public interface AVTimeSeriesDailyDeserializer {
     AVTimeSeriesDailyResponse deserialize(String AVTimeSeriesDailyResStr, String date) throws IOException,
            IllegalArgumentException;
}
