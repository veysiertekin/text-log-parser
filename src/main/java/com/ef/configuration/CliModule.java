package com.ef.configuration;

import com.ef.cli.model.converter.DateConverter;
import com.ef.cli.service.CliService;
import com.ef.cli.delegate.ParserFacade;
import com.ef.cli.service.impl.CliServiceImpl;
import com.ef.cli.delegate.impl.ParserFacadeImpl;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import picocli.CommandLine;

import java.util.Date;

public class CliModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<CommandLine.ITypeConverter<Date>>() {
        }).to(DateConverter.class);

        bind(CliService.class).to(CliServiceImpl.class);
        bind(ParserFacade.class).to(ParserFacadeImpl.class);
    }
}
