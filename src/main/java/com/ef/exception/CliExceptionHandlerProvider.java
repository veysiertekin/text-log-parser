package com.ef.exception;

import picocli.CommandLine;

import javax.inject.Inject;
import java.util.Set;

public class CliExceptionHandlerProvider {
    private final Set<CliExceptionHandlingStrategy> exceptionHandlingStrategies;

    @Inject
    public CliExceptionHandlerProvider(Set<CliExceptionHandlingStrategy> exceptionHandlingStrategies) {
        this.exceptionHandlingStrategies = exceptionHandlingStrategies;
    }

    public void handle(CommandLine commandLine, Exception ex) {
        for (CliExceptionHandlingStrategy cliExceptionHandlingStrategy : exceptionHandlingStrategies) {
            if (cliExceptionHandlingStrategy.satisfies(ex)) {
                cliExceptionHandlingStrategy.handle(commandLine, ex);
                return;
            }
        }
    }
}
