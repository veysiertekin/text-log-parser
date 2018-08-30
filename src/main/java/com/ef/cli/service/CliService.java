package com.ef.cli.service;

import com.ef.cli.model.exception.UnableToParseParameters;

public interface CliService {
    <T> T parse(T command, String[] args) throws UnableToParseParameters;

    String usage(Object command);
}
