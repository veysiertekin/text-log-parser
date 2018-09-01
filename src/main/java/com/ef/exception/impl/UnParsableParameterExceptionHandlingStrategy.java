package com.ef.exception.impl;

import com.ef.cli.model.exception.UnableToParseParameters;
import com.ef.cli.service.CliService;
import com.ef.exception.CliExceptionHandlingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import javax.inject.Inject;


/**
 * Print details of the missing parameters and the command-line usage
 *
 * @author veysiertekin
 */
public class UnParsableParameterExceptionHandlingStrategy extends CliExceptionHandlingStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnParsableParameterExceptionHandlingStrategy.class);
    private final CliService cliService;

    @Inject
    public UnParsableParameterExceptionHandlingStrategy(CliService cliService) {
        this.cliService = cliService;
    }

    /**
     * @see com.ef.exception.CliExceptionHandlingStrategy#satisfies(Exception)
     */
    @Override
    protected boolean satisfies(Exception e) {
        return e instanceof UnableToParseParameters;
    }

    /**
     * @see com.ef.exception.CliExceptionHandlingStrategy#handle(CommandLine, Exception)
     */
    @Override
    public void handle(CommandLine command, Exception e) {
        LOGGER.debug("Missing parameters!", e);

        String usageText = cliService.usage(command);
        LOGGER.error(usageText);
    }
}
