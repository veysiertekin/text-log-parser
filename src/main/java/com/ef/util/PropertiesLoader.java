package com.ef.util;

import com.ef.cli.model.dto.ApplicationProperties;
import com.ef.spark.service.SparkEtlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

public class PropertiesLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);
    private static final String APPLICATION_YML_NAME = "application.yml";

    public static ApplicationProperties loadProperties() throws IOException {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(false);

        ApplicationProperties properties;
        Yaml yml = new Yaml(loaderOptions);
        try {
            properties = yml.loadAs(APPLICATION_YML_NAME, ApplicationProperties.class);
        } catch (Exception e) {
            LOGGER.warn("There isn't any external `application.yml` file. Fall backing to internal configuration...");
            try (InputStream ymlAsStream = PropertiesLoader.class.getResourceAsStream("/" + APPLICATION_YML_NAME)) {
                properties = yml.loadAs(ymlAsStream, ApplicationProperties.class);
            }
        }
        LOGGER.info("Configuration file has been loaded successfully: {}", APPLICATION_YML_NAME);
        return properties;
    }
}
