package com.ef.exception;


import picocli.CommandLine;

public abstract class CliExceptionHandlingStrategy {
    protected abstract boolean satisfies(Exception e);

    protected abstract void handle(CommandLine command, Exception e);
}
