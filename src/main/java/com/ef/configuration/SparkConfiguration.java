package com.ef.configuration;

import com.ef.cli.model.dto.ApplicationProperties;
import org.apache.spark.SparkConf;

import javax.inject.Inject;

/**
 * Apache Spark configuration properties
 *
 * @author veysiertekin
 */
public class SparkConfiguration extends SparkConf {
    private static final long serialVersionUID = -8541870740602884255L;

    private static final String UI_PORT_KEY = "spark.ui.port";
    private static final String DRIVER_HOST_KEY = "spark.driver.host";

    @Inject
    public SparkConfiguration(PropertiesConfiguration configuration) {
        ApplicationProperties.SparkProperties sparkProperties = configuration.properties().getSpark();
        setMaster(sparkProperties.getMaster());
        setAppName(sparkProperties.getAppName());
        set(UI_PORT_KEY, sparkProperties.getUi().getPort().toString());
        set(DRIVER_HOST_KEY, sparkProperties.getUi().getHost());
    }
}
