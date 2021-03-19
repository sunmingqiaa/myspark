package jdvaAppender;

import org.junit.Test;


public class SparkDatabaseLoggerTest {
    @Test
    public void sendLogToDatabase(){
        SparkDatabaseLogger logger = new SparkDatabaseLogger("123", "12", "test", 11, 11, 12, 13, "w", "1", "2");
        logger.sendLogToDatabase();
    }

}