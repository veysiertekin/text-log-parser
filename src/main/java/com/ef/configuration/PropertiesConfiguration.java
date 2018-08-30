package com.ef.configuration;

import com.ef.cli.model.dto.ApplicationProperties;
import com.ef.util.PropertiesLoader;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class PropertiesConfiguration {
    private final ApplicationProperties properties;

    public PropertiesConfiguration() throws IOException {
        this.properties = PropertiesLoader.loadProperties();
    }

    public ApplicationProperties properties() {
        return properties;
    }
}
