package com.ef.cli.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Getter
@Setter
@Command
public class ParserOptions {
    @Option(names = {"-startDate", "--startDate"}, required = true, description = "Start date in format of 'yyyy-MM-dd.HH:mm:ss'")
    private Date startDate;

    @Option(names = {"-duration", "--duration"}, required = true, description = "'hourly' or 'daily'")
    private Duration duration;

    @Option(names = {"-threshold", "--threshold"}, required = true, description = "Any integer value")
    private Integer threshold;
}
