package com.ef.cli.delegate.impl;

import com.ef.cli.delegate.LogProcessorFacade;
import com.ef.cli.model.dto.ParserOptions;
import com.ef.cli.model.exception.ParseValidationException;
import com.ef.cli.model.exception.UnableToParseParameters;
import com.ef.cli.service.CliService;
import com.ef.cli.service.EtlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @see com.ef.cli.delegate.LogProcessorFacade
 */
public class LogProcessorFacadeImpl implements LogProcessorFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogProcessorFacadeImpl.class);

    private final CliService cliService;
    private final EtlService etlService;

    @Inject
    public LogProcessorFacadeImpl(CliService cliService, EtlService etlService) {
        this.cliService = cliService;
        this.etlService = etlService;
    }

    /**
     * @see com.ef.cli.delegate.LogProcessorFacade#process(String[])
     */
    @Override
    public void process(String[] args) {
        try {
            ParserOptions options = parseOptions(args);
            etlService.parseLogFile(options);
        } catch (UnableToParseParameters up) {
            LOGGER.debug("Missing parameters!", up);
            printParserUsage();
        } catch (ParseValidationException pve) {
            LOGGER.error("Validation exception: {}", pve.getMessage());
        }
    }

    private ParserOptions parseOptions(String[] args) throws UnableToParseParameters, ParseValidationException {
        ParserOptions parserOptions = cliService.parse(new ParserOptions(), args);
        validateFile(parserOptions);
        return parserOptions;
    }

    private void validateFile(ParserOptions parserOptions) throws ParseValidationException {
        if (!parserOptions.getAccessLog().exists()) {
            throw new ParseValidationException("File not exists!");
        }
    }

    private void printParserUsage() {
        String usageText = cliService.usage(new ParserOptions());
        LOGGER.error(usageText);
    }
}
