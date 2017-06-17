package cn.tim.integration.consumer;

import cn.tim.integration.Application;
import cn.tim.integration.producer.ProducerApplication;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by LuoLiBing on 16/1/21.
 */
public class ConsumerApplicationTests {

    private static ConfigurableApplicationContext context;

    @BeforeClass
    public static void beforeClass() {
        context = SpringApplication.run(Application.class);
    }

    @AfterClass
    public static void afterClass() {
        if(context != null) {
            context.close();
        }
    }

    @Before
    public void deleteOutput() {
        //FileSystemUtils.deleteRecursively(Paths.get("target/output").toFile());
    }

    @Test
    public void testVanillaExchange() {
        SpringApplication.run(ProducerApplication.class,  "world");

    }
}
