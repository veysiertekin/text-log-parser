package com.ef.cli.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.LocalDateTime;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Getter
@Setter
@Command
public class ParserOptions {
    @Option(names = "--accesslog", required = true, description = "Log file location")
    private File accessLog;

    @Option(names = "--startDate", required = true, description = "Start date in format of 'yyyy-MM-dd.HH:mm:ss'")
    private LocalDateTime startDate;

    @Option(names = "--duration", required = true, description = "'hourly' or 'daily'")
    private Duration duration;

    @Option(names = "--threshold", required = true, description = "Threshold limit for blocking IPs; any integer value")
    private Integer threshold;
}
