package com.ef.exception.impl;

import com.ef.cli.model.exception.ParseValidationException;
import com.ef.exception.CliExceptionHandlingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

public class ParseValidationExceptionHandlingStrategy extends CliExceptionHandlingStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseValidationExceptionHandlingStrategy.class);

    @Override
    protected boolean satisfies(Exception e) {
        return e instanceof ParseValidationException;
    }

    @Override
    public void handle(CommandLine command, Exception e) {
        LOGGER.error("Validation exception: {}", e.getMessage());
    }
}
