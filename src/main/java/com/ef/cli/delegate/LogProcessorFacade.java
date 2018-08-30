package com.ef.cli.delegate;

/**
 * Provides a bridge between cli and etl service operations at the parsing phase.
 *
 * @author veysiertekin
 */
public interface LogProcessorFacade {
    /**
     * Starts parsing operation
     *
     * @param args log parsing parameters
     */
    void process(String[] args);
}
