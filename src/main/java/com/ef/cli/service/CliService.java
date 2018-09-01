package com.ef.cli.service;

import com.ef.cli.model.exception.UnableToParseParameters;
import picocli.CommandLine;

public interface CliService {
    /**
     * Parses cli arguments into required command object
     *
     * @param args        cli arguments
     * @param commandLine cli command object like {@link com.ef.cli.model.dto.ParserOptions}
     * @throws UnableToParseParameters if any argument can not be parsed
     */
    void parse(String[] args, CommandLine commandLine) throws UnableToParseParameters;

    /**
     * Prints the usage of given cli command object
     *
     * @param commandLine cli command object
     * @return {@link java.lang.String} usage description of cli object
     */
    String usage(CommandLine commandLine);
}
