package com.ef.exception.impl;

import com.ef.exception.CliExceptionHandlingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

public class DefaultExceptionHandlingStrategy extends CliExceptionHandlingStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandlingStrategy.class);

    @Override
    protected boolean satisfies(Exception e) {
        return true;
    }

    @Override
    public void handle(CommandLine command, Exception e) {
        LOGGER.debug("Unknown exception!", e);
        LOGGER.error("Unknown exception: {}", e.getCause().getMessage());
    }
}
