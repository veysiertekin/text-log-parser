package com.ef;

import com.ef.cli.delegate.LogProcessorFacade;
import com.ef.module.ParserModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


public class Parser {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ParserModule());
        LogProcessorFacade logProcessorFacade = injector.getInstance(LogProcessorFacade.class);
        logProcessorFacade.process(args);
    }
}
