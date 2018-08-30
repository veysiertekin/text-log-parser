package com.ef.module;

import com.ef.cli.delegate.LogProcessorFacade;
import com.ef.cli.delegate.impl.LogProcessorFacadeImpl;
import com.ef.cli.model.converter.DateConverter;
import com.ef.cli.service.CliService;
import com.ef.cli.service.EtlService;
import com.ef.cli.service.impl.CliServiceImpl;
import com.ef.configuration.DbConfiguration;
import com.ef.configuration.SparkConfiguration;
import com.ef.spark.service.SparkEtlService;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.apache.spark.SparkConf;
import picocli.CommandLine;

import java.time.LocalDateTime;

public class ParserModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DbConfiguration.class);
        bind(SparkConfiguration.class);
        bind(dateTimeConverterType()).to(DateConverter.class);
        bind(SparkConf.class).to(SparkConfiguration.class);
        bind(EtlService.class).to(SparkEtlService.class);
        bind(CliService.class).to(CliServiceImpl.class);
        bind(LogProcessorFacade.class).to(LogProcessorFacadeImpl.class);
    }

    private TypeLiteral<CommandLine.ITypeConverter<LocalDateTime>> dateTimeConverterType() {
        return new TypeLiteral<CommandLine.ITypeConverter<LocalDateTime>>() {
        };
    }
}
