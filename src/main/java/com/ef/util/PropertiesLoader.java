package com.ef.util;

import com.ef.cli.model.dto.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

/**
 * Properties loading utility
 *
 * @author veysiertekin
 */
public class PropertiesLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);
    private static final String APPLICATION_YML_NAME = "application.yml";

    /**
     * Loads application properties
     *
     * @return application properties
     * @throws IOException if any error happen while closing resource
     */
    public static ApplicationProperties loadProperties() throws IOException {
        Yaml yml = new Yaml(yamlOptions());
        ApplicationProperties properties = load(yml);
        LOGGER.info("Configuration file has been loaded successfully: {}", APPLICATION_YML_NAME);
        return properties;
    }

    private static ApplicationProperties load(Yaml yml) throws IOException {
        try {
            return loadExternal(yml);
        } catch (Exception e) {
            LOGGER.warn("There isn't any external `{}` file. Fall backing to internal configuration...", APPLICATION_YML_NAME);
            return loadInternal(yml);
        }
    }

    /**
     * Load external yml file
     *
     * @param yml Yaml file options
     * @return application properties
     * @throws IOException if any error happen while closing resource
     */
    private static ApplicationProperties loadInternal(Yaml yml) throws IOException {
        try (InputStream ymlAsStream = PropertiesLoader.class.getResourceAsStream("/" + APPLICATION_YML_NAME)) {
            return yml.loadAs(ymlAsStream, ApplicationProperties.class);
        }
    }

    /**
     * Load internal yml file in the classpath
     *
     * @param yml Yaml file options
     * @return application properties
     */
    private static ApplicationProperties loadExternal(Yaml yml) {
        return yml.loadAs(APPLICATION_YML_NAME, ApplicationProperties.class);
    }

    private static LoaderOptions yamlOptions() {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(false);
        return loaderOptions;
    }
}
