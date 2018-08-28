package com.ef.cli.service;

import com.ef.cli.model.exception.UnableToParse;

public interface CliService {
    <T> T parse(T command, String[] args) throws UnableToParse;

    String usage(Object command);
}
