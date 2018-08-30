# Log Parser

A cli log parser based in Apache Spark. After parsing, cli utility pushes the data to a remote DB by using jdbc connection. By default, it try to push data into local MySQL instance, but you can change the configuration externally (see below sections).

## Building Sources

ℹ️  Make sure your system have `java 8` installed on it, and please verify that `maven` will run with `java 1.8`. If default `java` version is different than required version, you can set java home by `export JAVA_HOME=<path-to-jdk>` [note: jdk path should be parent directory, not `bin`]

```bash
➜  ./mvnw -version
Apache Maven 3.5.2 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T10:58:13+03:00)
Maven home: /Users/<user>/.m2/wrapper/dists/apache-maven-3.5.2-bin/28qa8v9e2mq69covern8vmdkj0/apache-maven-3.5.2
Java version: 1.8.0_181, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/jre
Default locale: en_TR, platform encoding: UTF-8
OS name: "mac os x", version: "10.13.6", arch: "x86_64", family: "mac"
```

You can build the project using:

```bash
➜  ./mvnw clean install
```

For generating javadocs:

```bash
➜  ./mvnw javadoc:javadoc
```

## Print Usage Directive

If any required parameter doesn't specified, program will print usage directive.

```bash
Usage: <main class> --accesslog=<accessLog> --duration=<duration>
                    --startDate=<startDate> --threshold=<threshold>
      --accesslog=<accessLog>
         Log file location
      --duration=<duration>
         'hourly' or 'daily'
      --startDate=<startDate>
         Start date in format of 'yyyy-MM-dd.HH:mm:ss'
      --threshold=<threshold>
         Threshold limit for blocking IPs; any integer value

```

## Configuration

Application has an internal configuration file with default values. But if you want, you can configure application properties externally. Just create a file named `application.yml` at the current directory.

#### Internal `application.yml`

Default internal `application.yml` configuration:

```yml
db:
  jdbcUrl:  "jdbc:mysql://localhost:3306/test?useSSL=false"
  user: root
  password: ""
  logsTable: "log"
  blockedIpsTable: "blocked_ip"

spark:
  master: "local[*]"
  appName:  "spark-app"
  ui:
    host: 127.0.0.1
    port: 4040
```

#### Application Configuration Parameters

|Parameter name| Description | Internal configuration value|
|---|---|---|
|db.jdbcUrl| Jdbc URL of the data source|`jdbc:mysql://localhost:3306/test?useSSL=false`|
|db.user| DB username|`root`|
|db.password| DB password| &lt;empty&gt;|
|db.logsTable| Persistence table of the logs| `log`|
|db.blockedIpsTable| Persistence table of the blocked IPs| `blocked_ip`|
|spark.master|Spark instances|`local[*]` (it will be started internally)|
|spark.appName|Spark application name that will be shown at the Spark UI|`spark-app`|
|spark.ui.host|Current host ip of the machine|`127.0.0.1`|
|spark.ui.port|Port of the Spark UI will be served|`4040`|

## Sample Cli Usages

```bash
➜  java -cp "target/parser.jar" com.ef.Parser --accesslog=data/access.log --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200
14:54:09.608 WARN  c.e.u.PropertiesLoader - There isn't any external `application.yml` file. Fall backing to internal configuration...
14:54:09.626 INFO  c.e.u.PropertiesLoader - Configuration file has been loaded successfully: application.yml
14:54:09.878 INFO  c.e.s.s.SparkEtlService - Initializing Apache Spark...
14:54:10.861 INFO  c.e.s.s.SparkEtlService - Data is being prepared...
14:54:13.436 INFO  c.e.s.s.SparkEtlService - All logs are being saved into DB...
14:54:21.158 INFO  c.e.s.s.SparkEtlService - Logs are being filtered between start and end dates...
14:54:21.232 INFO  c.e.s.s.SparkEtlService - Determining blocked IPs...
14:54:21.448 INFO  c.e.s.s.SparkEtlService - Blocked IPs are being saved into DB...
14:54:24.618 INFO  c.e.s.s.SparkEtlService - Had been blocked IPs:
14:54:25.194 INFO  c.e.s.s.SparkEtlService - 192.168.11.231
14:54:25.556 INFO  c.e.s.s.SparkEtlService - 192.168.106.134
```

```bash
➜  java -cp "target/parser.jar" com.ef.Parser --accesslog=data/access.log --startDate=2017-01-01.00:00:00 --duration=daily --threshold=500 
14:52:11.841 WARN  c.e.u.PropertiesLoader - There isn't any external `application.yml` file. Fall backing to internal configuration...
14:52:11.859 INFO  c.e.u.PropertiesLoader - Configuration file has been loaded successfully: application.yml
14:52:12.108 INFO  c.e.s.s.SparkEtlService - Initializing Apache Spark...
14:52:13.107 INFO  c.e.s.s.SparkEtlService - Data is being prepared...
14:52:15.507 INFO  c.e.s.s.SparkEtlService - All logs are being saved into DB...
14:52:22.873 INFO  c.e.s.s.SparkEtlService - Logs are being filtered between start and end dates...
14:52:22.944 INFO  c.e.s.s.SparkEtlService - Determining blocked IPs...
14:52:23.172 INFO  c.e.s.s.SparkEtlService - Blocked IPs are being saved into DB...
14:52:27.547 INFO  c.e.s.s.SparkEtlService - Had been blocked IPs:
14:52:28.447 INFO  c.e.s.s.SparkEtlService - 192.168.206.141
14:52:28.471 INFO  c.e.s.s.SparkEtlService - 192.168.33.16
14:52:28.521 INFO  c.e.s.s.SparkEtlService - 192.168.38.77
14:52:28.522 INFO  c.e.s.s.SparkEtlService - 192.168.129.191
14:52:28.574 INFO  c.e.s.s.SparkEtlService - 192.168.62.176
14:52:28.591 INFO  c.e.s.s.SparkEtlService - 192.168.51.205
14:52:28.627 INFO  c.e.s.s.SparkEtlService - 192.168.143.177
14:52:28.719 INFO  c.e.s.s.SparkEtlService - 192.168.162.248
14:52:28.848 INFO  c.e.s.s.SparkEtlService - 192.168.31.26
14:52:28.885 INFO  c.e.s.s.SparkEtlService - 192.168.102.136
14:52:29.040 INFO  c.e.s.s.SparkEtlService - 192.168.52.153
14:52:29.042 INFO  c.e.s.s.SparkEtlService - 192.168.185.164
14:52:29.056 INFO  c.e.s.s.SparkEtlService - 192.168.199.209
14:52:29.060 INFO  c.e.s.s.SparkEtlService - 192.168.203.111
14:52:29.117 INFO  c.e.s.s.SparkEtlService - 192.168.219.10
```


## DB Schema

(Updated DB schema is under the test resources)

```sql
CREATE SCHEMA IF NOT EXISTS test;
USE test;

CREATE TABLE IF NOT EXISTS  `log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(15) NOT NULL DEFAULT '',
  `request` text NOT NULL,
  `status` int(11) NOT NULL,
  `userAgent` varchar(512) NOT NULL DEFAULT '',
  `insertDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `logs_ip_index` (`ip`),
  KEY `logs_date_index` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `blocked_ip` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(15) NOT NULL DEFAULT '',
  `count` int(11) NOT NULL,
  `comment` varchar(250) NOT NULL DEFAULT '',
  `insertDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `blocked_ip_index` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## SQL Queries

1- Find IPs that made more than a certain number of requests for a given time period

```sql
select ip, count(*) as count
from log
where date between :startDate and :endDate
group by ip having count>:threshold order by count desc;
```

Example:

```sql
select ip, count(*) as count
from log
where date between '2017-01-01 01:18:50' and '2017-01-01 07:18:50'
group by ip having count>10 order by count desc;
```

2- Find requests made by a given IP

```sql
select *
from log
where ip=:ip
```

Example:

```sql
select *
from log
where ip='192.168.106.33'
```