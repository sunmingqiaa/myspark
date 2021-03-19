package jdvaAppender;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *@author: smq
 *@date: 2021/3/18
 */
public class SparkDatabaseLogger implements DatabaseLoggerBase{
    private static final Logger logger = LoggerFactory.getLogger(SparkDatabaseLogger.class);
    private String batchTime;
    private String applicationId;
    private String topicName;
    private int successNumRecord;
    private int failureNumRecord;
    private int inRate;
    private int outRate;

    private String batchStartTime;
    private String batchEndTime;
    private String avgDelayTimePerCount;

    @Override
    public String toString() {
        return "SparkDatabaseLogger{" +
                "batchTime='" + batchTime + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", topicName='" + topicName + '\'' +
                ", successNumRecord=" + successNumRecord +
                ", failureNumRecord=" + failureNumRecord +
                ", inRate=" + inRate +
                ", outRate=" + outRate +
                ", batchStartTime='" + batchStartTime + '\'' +
                ", batchEndTime='" + batchEndTime + '\'' +
                ", avgDelayTimePerCount='" + avgDelayTimePerCount + '\'' +
                '}';
    }

    public SparkDatabaseLogger(String batchTime, String applicationId, String topicName, int successNumRecord, int failureNumRecord, int inRate, int outRate, String batchStartTime, String batchEndTime, String avgDelayTimePerCount) {
        this.batchTime = batchTime;
        this.applicationId = applicationId;
        this.topicName = topicName;
        this.successNumRecord = successNumRecord;
        this.failureNumRecord = failureNumRecord;
        this.inRate = inRate;
        this.outRate = outRate;
        this.batchStartTime = batchStartTime;
        this.batchEndTime = batchEndTime;
        this.avgDelayTimePerCount = avgDelayTimePerCount;
        logger.info("hh");
    }

    @Override
    public void sendLogToDatabase() {
        MDC.put("batchTime",batchTime);
        MDC.put("applicationId",applicationId);
        MDC.put("topicName",topicName);
        MDC.put("successNumRecord", String.valueOf(successNumRecord));
        MDC.put("failureNumRecord", String.valueOf(failureNumRecord));
        MDC.put("inRate", String.valueOf(inRate));
        MDC.put("outRate", String.valueOf(outRate));
        MDC.put("batchStartTime",batchStartTime);
        MDC.put("batchEndTime",batchEndTime);
        MDC.put("avgDelayTimePerCount",avgDelayTimePerCount);
    }
}
