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

    /**
     * @see CliService#parse(String[], CommandLine)
     */
    @Override
    public void parse(String[] args, CommandLine commandLine) throws UnableToParseParameters {
        try {
            commandLine.registerConverter(LocalDateTime.class, dateConverter);
            commandLine.parse(args);
        } catch (CommandLine.ParameterException pe) {
            throw new UnableToParseParameters(pe);
        }
    }

    /**
     * @see com.ef.cli.service.CliService#usage(CommandLine)
     */
    @Override
    public String usage(CommandLine command) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine.usage(command, new PrintStream(outputStream), CommandLine.Help.Ansi.OFF);
        return outputStream.toString();
    }
}
