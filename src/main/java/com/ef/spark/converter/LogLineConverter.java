package com.ef.spark.converter;

import com.ef.spark.model.LogLine;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Converts regular java string array to Spark object
 *
 * @author veysiertekin
 */
public class LogLineConverter {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Converts string line input to Spark Distributed Object
     * It's static because of Spark distribution restrictions.
     *
     * @param line each line of input log file
     * @return Spark distributed object
     */
    public static LogLine toLogLine(String[] line) {
        LogLine logLine = new LogLine();
        logLine.setDate(parseTimestamp(line[0]));
        logLine.setIp(line[1]);
        logLine.setRequest(line[2]);
        logLine.setStatus(Integer.parseInt(line[3]));
        logLine.setUserAgent(line[4]);
        return logLine;
    }

    private static Timestamp parseTimestamp(String text) {
        LocalDateTime dateTime = LocalDateTime.from(DATE_TIME_FORMATTER.parse(text));
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Timestamp.from(instant);
    }
}
