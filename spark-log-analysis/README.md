# Spark Log Analysis

This project performs log analysis using Scala and Apache Spark.

## Technologies Used

- Scala
- Apache Spark 3.5.3
- Spark SQL
- SBT

## Dataset

The project uses an application log file containing:

- Timestamp
- Log level
- IP address
- URL
- HTTP status
- Response time

## Analysis Performed

- Read log data into a Spark DataFrame
- Renamed DataFrame columns
- Converted timestamp to Timestamp type
- Displayed schema
- Calculated total requests
- Calculated total errors
- Calculated successful requests
- Found most accessed URLs
- Found most active IP addresses
- Calculated average response time
- Identified slow requests
- Calculated URL performance
- Analyzed errors by URL
- Analyzed HTTP status codes
- Calculated error percentage

## Results

- Total Requests: 10
- Total Errors: 3
- Successful Requests: 7
- Most Accessed URL: `/products`
- Most Active IP: `192.168.1.10`
- Average Response Time: 304 ms
- Error Percentage: 30%

## Project Structure

```text
spark-log-analysis/
├── build.sbt
├── project/
│   └── build.properties
├── data/
│   └── application.log
├── src/
│   └── main/
│       └── scala/
│           └── LogAnalysis.scala
└── README.md
