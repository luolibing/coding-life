package cn.tim;

import cn.tim.demo2.Runner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 15/10/21.
 */
public class Application implements CommandLineRunner {

    public static void main(String[] args) throws IOException, TimeoutException {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*Runner runner = new Runner();
        runner.run();*/
        Runner runner = new Runner();
        runner.run();
        Collections.emptySet();
    }
}
