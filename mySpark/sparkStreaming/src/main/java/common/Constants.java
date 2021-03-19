package common;

/**
 * @author: smq
 * @date: 2021/3/19
 */
public class Constants {
    /*
     * spark 参数
     * */
    public static final String SPARKSTREAMING_APP_NAME = "sparkstreaming_app_name";
    public static final String SPARKSTREAMING_BATCH_TIME = "sparkstreaming_batch_time";
    public static final String SPARKSTREAMING_KAFKA_TOPIC = "sparkstreaming_kafka_topic";
    public static final String SPARKSTREAMING_IS_REPARTITION = "sparkstreaming_is_repartition";
    public static final String SPARKSTREAMING_NUM_PARTITION = "sparkstreaming_num_partition";
    public static final String SPARK_CONF_PROPERTIES = "spark_conf_properties";
    /*
     *kafka参数
     */
    public static final String GROUP_ID = "group_id";
    public static final String BOOTSTRAP_SERVERS = "bootstrap_servers";
    public static final String KAFKA_PRIOPERTIES = "kafka_prioperties";

    /*
     * handler
     * */
    public static final String MESSAGE_HANDLER_CLASS = "message_handler_class";

    /*hbase
     * */
    public static final String ROWKEY_NAME = "rowkey_name";
    public static final String TABLE_NAME = "table_name";
    public static final String FAMILY_NAME = "family_name";

}
