package com.ef.cli.model.converter;

import picocli.CommandLine;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements CommandLine.ITypeConverter<Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd.HH:mm:ss";

    @Override
    public Date convert(String value) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.parse(value);
    }
}
