package com.ef.spark.service;

import com.ef.cli.model.dto.ParserOptions;
import com.ef.cli.service.EtlService;
import com.ef.configuration.DbConfiguration;
import com.ef.spark.converter.LogLineConverter;
import com.ef.spark.model.LogLine;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.time.LocalDateTime;

import static com.ef.spark.converter.LogLineConverter.DATE_TIME_FORMATTER;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lit;

public class SparkEtlService implements EtlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SparkEtlService.class);

    private static final String LOG_DELIMITER = "\\|";

    private final SparkConf sparkConf;
    private final DbConfiguration dbConf;

    @Inject
    public SparkEtlService(SparkConf sparkConf, DbConfiguration dbConf) {
        this.sparkConf = sparkConf;
        this.dbConf = dbConf;
    }

    @Override
    public void parseLogFile(ParserOptions options) {
        LOGGER.info("Initializing Apache Spark...");
        try (SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate()) {
            File logFile = options.getAccessLog();
            LocalDateTime startDate = options.getStartDate();
            Integer threshold = options.getThreshold();
            LocalDateTime endDate = options.getDuration().addTo(startDate);

            Dataset<Row> logs = prepareDataForProcessing(spark, logFile);
            saveLogs(logs);

            Dataset<Row> logsBetweenDates = filterInRange(logs, startDate, endDate);
            Dataset<Row> blockedIpDs = determineBlockedIps(logsBetweenDates, threshold);

            saveAndPrintBlockedIps(threshold, blockedIpDs);
        }
    }

    private Dataset<Row> prepareDataForProcessing(SparkSession spark, File logFile) {
        LOGGER.info("Data is being prepared...");

        JavaRDD<LogLine> distData = spark.read().textFile(logFile.getAbsolutePath())
                .javaRDD()
                .map(line -> line.split(LOG_DELIMITER))
                .map(LogLineConverter::toLogLine);

        return spark.createDataFrame(distData, LogLine.class);
    }

    private void saveLogs(Dataset<Row> logs) {
        LOGGER.info("All logs are being saved...");
        logs.write().mode(SaveMode.Append)
                .jdbc(dbConf.getJdbcUrl(), dbConf.getLogsTable(), dbConf.getConnectionProperties());
    }

    private Dataset<Row> filterInRange(Dataset<Row> logs, LocalDateTime startDate, LocalDateTime endDate) {
        LOGGER.info("Logs are being filtered between start and end dates...");

        String startDateStr = DATE_TIME_FORMATTER.format(startDate);
        String endDateStr = DATE_TIME_FORMATTER.format(endDate);
        return logs.filter(col("date").between(lit(startDateStr), lit(endDateStr)));
    }

    private Dataset<Row> determineBlockedIps(Dataset<Row> logs, Integer threshold) {
        LOGGER.info("Determining blocked IPs...");
        return logs.groupBy(logs.col("ip")).count().filter("`count` >= " + threshold);
    }

    private void saveAndPrintBlockedIps(Integer threshold, Dataset<Row> blockedIpDs) {
        if (isDatasetEmpty(blockedIpDs)) {
            LOGGER.info("There isn't any blocked IP.");
        } else {
            saveBlockedIps(threshold, blockedIpDs);
            printBlockedIps(blockedIpDs);
        }
    }

    private void saveBlockedIps(Integer threshold, Dataset<Row> blockedIpDs) {
        LOGGER.info("Blocked IPs are being saved...");
        blockedIpDs
                .withColumn("comment", lit("IP have been blocked due to threshold limit of " + threshold))
                .write().mode(SaveMode.Append)
                .jdbc(dbConf.getJdbcUrl(), dbConf.getBlockedIpsTable(), dbConf.getConnectionProperties());
    }

    private void printBlockedIps(Dataset<Row> blockedIpDs) {
        LOGGER.info("These IPs had been blocked:");
        blockedIpDs.foreach(x -> LOGGER.info(x.getString(x.fieldIndex("ip"))));
    }

    private boolean isDatasetEmpty(Dataset<Row> ds) {
        return ((Row[]) ds.head(1)).length == 0;
    }
}
