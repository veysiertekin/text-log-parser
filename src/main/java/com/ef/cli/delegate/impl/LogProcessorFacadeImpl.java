package com.ef.cli.delegate.impl;

import com.ef.cli.delegate.LogProcessorFacade;
import com.ef.cli.model.dto.ParserOptions;
import com.ef.cli.model.exception.ParseValidationException;
import com.ef.cli.service.CliService;
import com.ef.cli.service.EtlService;
import com.ef.exception.CliExceptionHandlerProvider;
import picocli.CommandLine;

import javax.inject.Inject;

/**
 * @see com.ef.cli.delegate.LogProcessorFacade
 */
public class LogProcessorFacadeImpl implements LogProcessorFacade {
    private final CliService cliService;
    private final EtlService etlService;
    private final CliExceptionHandlerProvider cliExceptionHandlerProvider;

    @Inject
    public LogProcessorFacadeImpl(CliService cliService,
                                  EtlService etlService,
                                  CliExceptionHandlerProvider cliExceptionHandlerProvider) {
        this.cliService = cliService;
        this.etlService = etlService;
        this.cliExceptionHandlerProvider = cliExceptionHandlerProvider;
    }

    /**
     * @see com.ef.cli.delegate.LogProcessorFacade#process(String[])
     */
    @Override
    public void process(String[] args) {
        ParserOptions command = new ParserOptions();
        CommandLine commandLine = new CommandLine(command);
        try {
            cliService.parse(args, commandLine);
            validateFile(command);
            etlService.startEtl(command);
        } catch (Exception exception) {
            cliExceptionHandlerProvider.handle(commandLine, exception);
        }
    }

    private void validateFile(ParserOptions parserOptions) throws ParseValidationException {
        if (!parserOptions.getAccessLog().exists()) {
            throw new ParseValidationException("File not exists!");
        }
    }
}
