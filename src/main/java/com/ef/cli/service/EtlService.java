package com.ef.cli.service;

import com.ef.cli.model.dto.ParserOptions;

/**
 * Provides parsing and data transfer operations
 *
 * @author veysiertekin
 */
public interface EtlService {
    /**
     * Parses and persists given log file.
     *
     * @param options log file and time restrictions
     */
    void parseLogFile(ParserOptions options);
}
