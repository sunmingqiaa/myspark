package common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * @author: smq
 * @date: 2021/3/19
 */
public class PropertiesUtils {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    /**注意：1.当传入参数为文件名时,文件应再resources目录下,例如：resources/test。properties。而不能是resources/test。propertiesconf/这样在resources目录下还有目录
     * 2.建议使用文件路径，注意部署在linux服务器时使用绝对路径，避免找不到文件
     * @param propertiesPathOrName 文件名或文件路径
     * @Description:从指定文件获取 Propertis
     * @return: java.util.Properties
     */
    public static Properties getproperties(String propertiesPathOrName) {
        Properties properties = new Properties();
        File file = new File(propertiesPathOrName);
        try {
            if (file.exists() && file.isFile()) {
                properties.load(new FileReader(file));
                return properties;

            } else {
                InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesPathOrName);
                if (inputStream != null) {

                    properties.load(inputStream);
                    return properties;

                } else {
                    logger.error(propertiesPathOrName + "not found");
                    throw new IllegalArgumentException(propertiesPathOrName + " not found");
                }
            }
        } catch (IOException e) {
            logger.error("can not read properties{},the error info is :{}", e.getCause());
            throw new RuntimeException(e.getCause());
        }

    }

    /**
     * @param propertiesPathOrName
     * @Description:将配置转换成map集合
     * @return: java.util.Map<java.lang.String, java.lang.String>
     */
    public static Map<String, String> getPropertiesAsMap(String propertiesPathOrName) {
        return (Map) getproperties(propertiesPathOrName);
    }

    /**
     * @param propertiesPathOrName
     * @Description:将配置文件的配置转换成set
     * @return: java.util.Set<scala.Tuple2 < java.lang.String, java.lang.String>>
     */
    public static Set<Tuple2<String, String>> getPropertiesAsSet(String propertiesPathOrName) {
        Set hashSet = new HashSet<>();
        Properties properties = getproperties(propertiesPathOrName);
        for (Map.Entry entry : properties.entrySet()) {
            hashSet.add(new Tuple2<>(entry.getKey().toString(), entry.getValue().toString()));
        }
        return hashSet;
    }
}
