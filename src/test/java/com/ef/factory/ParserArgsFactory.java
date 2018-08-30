package com.ef.factory;

import com.ef.builder.ParserArgsBuilder;

import static com.ef.data.TestData.*;

public class ParserArgsFactory {
    public static String[] argsWithInvalidFileName() {
        return validArgsBuilder()
                .setAccessLog("%%%---.txt")
                .build();
    }

    public static String[] argsWithInvalidAccessLog() {
        return validArgsBuilder()
                .setAccessLog(null)
                .build();
    }

    public static String[] argsWithInvalidDuration() {
        return validArgsBuilder()
                .setDuration(null)
                .build();
    }

    public static String[] argsWithInvalidStartDate() {
        return validArgsBuilder()
                .setStartDate(null)
                .build();
    }

    public static String[] argsWithInvalidThreshold() {
        return validArgsBuilder()
                .setThreshold(null)
                .build();
    }

    private static ParserArgsBuilder validArgsBuilder() {
        return new ParserArgsBuilder()
                .setAccessLog(ACCESS_LOG)
                .setDuration(DURATION)
                .setStartDate(START_DATE)
                .setThreshold(THRESHOLD);
    }
}
