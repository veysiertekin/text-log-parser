package com.ef.factory;

import com.ef.builder.ParserArgsBuilder;

public class ParserArgsFactory {
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

    public static String[] validArgs() {
        return validArgsBuilder()
                .build();
    }

    private static ParserArgsBuilder validArgsBuilder() {
        return new ParserArgsBuilder()
                .setDuration("daily")
                .setStartDate("2017-01-01.13:00:00")
                .setThreshold("100");
    }
}
