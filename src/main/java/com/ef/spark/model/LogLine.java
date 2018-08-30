package com.ef.spark.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class LogLine implements Serializable {
    private static final long serialVersionUID = -5770717745326021354L;

    private Timestamp date;
    private String ip;
    private String request;
    private Integer status;
    private String userAgent;
}
