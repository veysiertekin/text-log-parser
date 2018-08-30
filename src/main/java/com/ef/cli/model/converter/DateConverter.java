package com.ef.cli.model.converter;

import picocli.CommandLine;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter implements CommandLine.ITypeConverter<LocalDateTime>, Serializable {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");

    @Override
    public LocalDateTime convert(String value) {
        return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
    }
}
