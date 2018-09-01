package com.ef.exception;


import picocli.CommandLine;

/**
 * Cli exception handling strategy
 *
 * @author veysiertekin
 */
public abstract class CliExceptionHandlingStrategy {
    /**
     * This method returns if it is matches the handler or not
     *
     * @param e exception
     * @return if it is matches the handler or not
     */
    protected abstract boolean satisfies(Exception e);

    /**
     * Executes post doings after exception occurs
     *
     * @param command input command object
     * @param e       exception have been thrown
     */
    protected abstract void handle(CommandLine command, Exception e);
}
