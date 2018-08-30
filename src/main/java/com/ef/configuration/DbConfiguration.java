package com.ef.configuration;

import com.ef.cli.model.dto.ApplicationProperties;
import lombok.Getter;

import javax.inject.Inject;
import java.util.Properties;

/**
 * Db configurations
 *
 * @author veysiertekin
 */
@Getter
public class DbConfiguration {
    private static final String USERNAME_KEY = "user";
    private static final String PASSWORD_KEY = "password";

    private String jdbcUrl;
    private Properties connectionProperties;

    private String logsTable;
    private String blockedIpsTable;

    @Inject
    public DbConfiguration(PropertiesConfiguration propertiesConfiguration) {
        ApplicationProperties.DbProperties properties = propertiesConfiguration.properties().getDb();

        this.jdbcUrl = properties.getJdbcUrl();
        this.logsTable = properties.getLogsTable();
        this.blockedIpsTable = properties.getBlockedIpsTable();

        this.connectionProperties = new Properties();
        connectionProperties.put(USERNAME_KEY, properties.getUser());
        connectionProperties.put(PASSWORD_KEY, properties.getPassword());
    }
}
