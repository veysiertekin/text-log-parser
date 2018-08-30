package com.ef.cli.service;

import com.ef.cli.model.dto.ParserOptions;

public interface EtlService {
    void parseLogFile(ParserOptions options);
}
