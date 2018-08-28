package com.ef;

import com.ef.cli.delegate.ParserFacade;
import com.ef.configuration.CliModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class Parser {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CliModule());
        ParserFacade parserFacade = injector.getInstance(ParserFacade.class);
        parserFacade.processIfSatisfies(args);
    }
}
