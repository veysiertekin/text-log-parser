package com.ef.cli.service;

import com.ef.cli.model.exception.UnableToParseParameters;

public interface CliService {
    /**
     * Parses cli arguments into required command object
     *
     * @param command cli command object like {@link com.ef.cli.model.dto.ParserOptions}
     * @param args    cli arguments
     * @param <T>     destination object
     * @return parsed cli command
     * @throws UnableToParseParameters if any argument can not be parsed
     */
    <T> T parse(T command, String[] args) throws UnableToParseParameters;

    /**
     * Prints the usage of given cli command object
     *
     * @param command cli command object
     * @return {@link java.lang.String} usage description of cli object
     */
    String usage(Object command);
}
