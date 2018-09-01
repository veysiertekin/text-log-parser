package com.ef.spark.service;

import com.ef.cli.model.converter.DateConverter;
import com.ef.cli.model.dto.Duration;
import com.ef.cli.model.dto.ParserOptions;
import com.ef.cli.service.EtlService;
import com.ef.configuration.DbConfiguration;
import com.ef.configuration.PropertiesConfiguration;
import com.ef.configuration.SparkConfiguration;
import extensions.CaptureSystemOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.ef.data.TestData.ACCESS_LOG;
import static com.ef.data.TestData.START_DATE;
import static org.hamcrest.core.StringContains.containsString;

@CaptureSystemOutput
class SparkEtlServiceTest {
    private static EtlService etlService;
    private static DateConverter dateConverter;

    @BeforeAll
    static void setUp() throws IOException {
        PropertiesConfiguration configuration = new PropertiesConfiguration();
        dateConverter = new DateConverter();
        etlService = new SparkEtlService(new SparkConfiguration(configuration), new DbConfiguration(configuration));
    }

    @Test
    void parse_log_file_with_blocked_ips(CaptureSystemOutput.OutputCapture outputCapture) {
        // Given
        ParserOptions options = new ParserOptions();
        options.setAccessLog(new File(ACCESS_LOG));
        options.setStartDate(dateConverter.convert(START_DATE));
        options.setDuration(Duration.hourly);
        options.setThreshold(10);

        // When
        etlService.startEtl(options);

        // Then
        outputCapture.expect(containsString("Had been blocked IPs:"));
    }

    @Test
    void parse_log_file_with_no_ip_blocked(CaptureSystemOutput.OutputCapture outputCapture) {
        // Given
        ParserOptions options = new ParserOptions();
        options.setAccessLog(new File(ACCESS_LOG));
        options.setStartDate(dateConverter.convert(START_DATE).plusMonths(1));
        options.setDuration(Duration.hourly);
        options.setThreshold(60);

        // When
        etlService.startEtl(options);

        // Then
        outputCapture.expect(containsString("There isn't any blocked IP."));
    }
}