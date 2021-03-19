package common;

import common.util.PropertiesUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.broadcast.Broadcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;
import scala.collection.JavaConversions;

import java.io.Serializable;
import java.util.*;

/**
 * 在driver端，初始化配置参数，广播变量
 *
 * @author: smq
 * @date: 2021/3/19
 */
public class Context implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Context.class);
    /*
     * sparkstreaming 配置对象*/
    private static final SparkConf SPARK_CONF = new SparkConf();
    /*
     * kafka配置参数集合*/
    private static final Map<String, Object> kafkaParams = new HashMap<>();

    /*
     * 自定义配置参数集合*/
    private static final Map<String, String> customerConf = new HashMap<>();

    /*
     * 连接hbase表配置广播变量*/
    private Broadcast<TableInfoConfig> broadcastTableInfo;
    /*
     * 存储文件时间戳*/
    private List<String> fileTimeList = new ArrayList<>();
    /*
     * 存储sparkstreaming的一批属据中，分区内最大，分区内最小的时间戳
     * */
    private final List<String> minMaxTimeList = new ArrayList<>();

    /*
     * 开始处理数据的时间*/
    private List<String> startTimeList = new ArrayList<>();

    /*
     * 存储整点标记
     * */
    private Integer completedHour = -1;

    /*
     * 程序的唯一标志*/
    private String applictionId;

    /*
     * 判断是否为windows*/
    private boolean isWindow = System.getProperty("os.name").startsWith("Windows");
    /*
     * 写入hbase成功的条数*/
    private int numSucceedRecords;

    /*
     * 整点无数据，等待批次数*/
    private int waitNumBatch;


    public static SparkConf getSparkConf() {
        return SPARK_CONF;
    }

    public static Map<String, Object> getKafkaParams() {
        return kafkaParams;
    }

    public static Map<String, String> getCustomerConf() {
        return customerConf;
    }

    public Broadcast<TableInfoConfig> getBroadcastTableInfo() {
        return broadcastTableInfo;
    }

    public void setBroadcastTableInfo(Broadcast<TableInfoConfig> broadcastTableInfo) {
        this.broadcastTableInfo = broadcastTableInfo;
    }

    public List<String> getFileTimeList() {
        return fileTimeList;
    }

    public void setFileTimeList(List<String> fileTimeList) {
        this.fileTimeList = fileTimeList;
    }

    public List<String> getMinMaxTimeList() {
        return minMaxTimeList;
    }

    public List<String> getStartTimeList() {
        return startTimeList;
    }

    public void setStartTimeList(List<String> startTimeList) {
        this.startTimeList = startTimeList;
    }

    public Integer getCompletedHour() {
        return completedHour;
    }

    public void setCompletedHour(Integer completedHour) {
        this.completedHour = completedHour;
    }

    public String getApplictionId() {
        return applictionId;
    }

    public void setApplictionId(String applictionId) {
        this.applictionId = applictionId;
    }

    public boolean isWindow() {
        return isWindow;
    }

    public void setWindow(boolean window) {
        isWindow = window;
    }

    public int getNumSucceedRecords() {
        return numSucceedRecords;
    }

    public void setNumSucceedRecords(int numSucceedRecords) {
        this.numSucceedRecords = numSucceedRecords;
    }

    public int getWaitNumBatch() {
        return waitNumBatch;
    }

    public void setWaitNumBatch(int waitNumBatch) {
        this.waitNumBatch = waitNumBatch;
    }

    /*--------------读取配置参数————————————————————*/
    public Context() {
    }

    /*
     * 初始化自定义配置的参数*/

    void initContext(Map<String, String> customerConf) {
        logger.warn("load customer properties: {} ", customerConf.toString());
        Precondition.checkNotNull(customerConf);
        this.customerConf.putAll(customerConf);
    }

    public Context(Map<String, String> customerConf) {
        initContext(customerConf);
    }

    /*
    * 初始化kafka连接参数
    * */
    public void initKafkaConf(){
//        初始化kafka配置参数
        Map<String, String> kafkaParams = PropertiesUtils.getPropertiesAsMap(Constants.KAFKA_PRIOPERTIES);
        this.kafkaParams.putAll(kafkaParams);

        for (Map.Entry<String,String> entry:customerConf.entrySet()){
            if (entry.getKey().startsWith("kafka")&&entry.getValue()!=null){
                this.kafkaParams.put(entry.getKey().substring(6),entry.getValue());

            }

        }
logger.warn("init kafkaParams : {}",kafkaParams.toString());
    }

    /*
    * 初始化spark配置参数*/

    public void initSparkConf(){
        Set<Tuple2<String, String>> sparkConf = PropertiesUtils.getPropertiesAsSet(Constants.SPARK_CONF_PROPERTIES);
        //设置参数
        SPARK_CONF.setAll(JavaConversions.asScalaSet(sparkConf));
//        设置spark程序名
        SPARK_CONF.setAppName(getAppName());
        //注册kryo序列化类
        SPARK_CONF.registerKryoClasses(new Class[]{ConsumerRecord.class});
        for (Map.Entry<String,String> entry:customerConf.entrySet()){
            if (entry.getKey().startsWith("spark")&&entry.getValue()!=null){
                SPARK_CONF.set(entry.getKey(),entry.getValue());
            }
        }
        logger.warn("init spark conf : {}",sparkConf.toString());
    }
/*
* 初始化streamingcontext对象
* */

    private String getAppName() {
        String appName =getCustomerConf().get(Constants.SPARKSTREAMING_APP_NAME);
        return appName;
    }
}
