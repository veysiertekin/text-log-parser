package com.ef.exception.impl;

import com.ef.cli.model.exception.ParseValidationException;
import com.ef.exception.CliExceptionHandlingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;


/**
 * Logs parsing errors
 *
 * @author veysiertekin
 */
public class ParseValidationExceptionHandlingStrategy extends CliExceptionHandlingStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseValidationExceptionHandlingStrategy.class);

    /**
     * @see com.ef.exception.CliExceptionHandlingStrategy#satisfies(Exception)
     */
    @Override
    protected boolean satisfies(Exception e) {
        return e instanceof ParseValidationException;
    }

    /**
     * @see com.ef.exception.CliExceptionHandlingStrategy#handle(CommandLine, Exception)
     */
    @Override
    public void handle(CommandLine command, Exception e) {
        LOGGER.error("Validation exception: {}", e.getMessage());
    }
}
