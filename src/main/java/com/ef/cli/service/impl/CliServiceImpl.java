package com.ef.cli.service.impl;

import com.ef.cli.model.exception.UnableToParseParameters;
import com.ef.cli.service.CliService;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

public class CliServiceImpl implements CliService {
    private final CommandLine.ITypeConverter<LocalDateTime> dateConverter;

    @Inject
    public CliServiceImpl(CommandLine.ITypeConverter<LocalDateTime> dateConverter) {
        this.dateConverter = dateConverter;
    }

    @Override
    public <T> T parse(T command, String[] args) throws UnableToParseParameters {
        try {
            CommandLine commandLine = new CommandLine(command);
            commandLine.registerConverter(LocalDateTime.class, dateConverter);
            commandLine.parse(args);
        } catch (CommandLine.ParameterException pe) {
            throw new UnableToParseParameters(pe);
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
