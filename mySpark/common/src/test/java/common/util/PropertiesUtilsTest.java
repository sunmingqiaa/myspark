package common.util;


public class PropertiesUtilsTest {

    @org.junit.Test
    public void getproperties() {
//        测试传入文件路径
        System.out.println(PropertiesUtils.getproperties("src/main/resources/test.properties").get("a"));
    }

    @org.junit.Test
    public void getPropertiesAsMap() {
//        测试传入文件路径
        System.out.println(PropertiesUtils.getPropertiesAsMap("src/main/resources/test.properties").get("a"));
    }

    @org.junit.Test
    public void getPropertiesAsSet() {
        System.out.println(PropertiesUtils.getPropertiesAsSet("test.properties"));

    }
}