package com.ef.cli.service.impl;

import com.ef.cli.model.exception.UnableToParse;
import com.ef.cli.service.CliService;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class CliServiceImpl implements CliService {
    @Inject
    private CommandLine.ITypeConverter<Date> dateConverter;

    @Override
    public <T> T parse(T command, String[] args) throws UnableToParse {
        try {
            CommandLine commandLine = new CommandLine(command);
            commandLine.registerConverter(Date.class, dateConverter);
            commandLine.parse(args);
        } catch (CommandLine.ParameterException pe) {
            throw new UnableToParse(pe);
        }
        return command;
    }

    @Override
    public String usage(Object command) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine.usage(command, new PrintStream(outputStream), CommandLine.Help.Ansi.OFF);
        return outputStream.toString();
    }
}
