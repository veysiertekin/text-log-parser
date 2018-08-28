package com.ef.cli.delegate.impl;

import com.ef.cli.delegate.ParserFacade;
import com.ef.cli.model.dto.ParserOptions;
import com.ef.cli.model.exception.UnableToParse;
import com.ef.cli.service.CliService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ParserFacadeImpl implements ParserFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParserFacadeImpl.class);

    @Inject
    private CliService cliService;

    @Override
    public void processIfSatisfies(String[] args) {
        try {
            parseAndProcessLogs(args);
        } catch (UnableToParse up) {
            LOGGER.debug("Missing parameters!", up);
            printParserUsage();
        }
    }

    private void parseAndProcessLogs(String[] args) throws UnableToParse {
        ParserOptions parserOptions = cliService.parse(new ParserOptions(), args);
    }

    private void printParserUsage() {
        String usageText = cliService.usage(new ParserOptions());
        LOGGER.error(usageText);
    }
}
