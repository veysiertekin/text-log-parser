package com.ef.exception.impl;

import com.ef.exception.CliExceptionHandlingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;


/**
 * This strategy runs if there isn't any suitable handler.
 *
 * @author veysiertekin
 */
public class DefaultExceptionHandlingStrategy extends CliExceptionHandlingStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandlingStrategy.class);

    /**
     * @see com.ef.exception.CliExceptionHandlingStrategy#satisfies(Exception)
     */
    @Override
    protected boolean satisfies(Exception e) {
        return true;
    }

    /**
     * @see com.ef.exception.CliExceptionHandlingStrategy#handle(CommandLine, Exception)
     */
    @Override
    public void handle(CommandLine command, Exception e) {
        LOGGER.debug("Unknown exception!", e);
        LOGGER.error("Unknown exception: {}", e.getCause().getMessage());
    }
}
