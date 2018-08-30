package com.ef.cli.model.dto;

import lombok.Data;

@Data
public class ApplicationProperties {
    private SparkProperties spark;
    private DbProperties db;

    @Data
    public static class SparkProperties {
        private String master;
        private String appName;
        private UiProperties ui;

        @Data
        public static class UiProperties {
            private String host;
            private Integer port;
        }
    }

    @Data
    public static class DbProperties {
        private String jdbcUrl;
        private String user;
        private String password;
        private String logsTable;
        private String blockedIpsTable;
    }
}
