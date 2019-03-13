package com.tim.zookeeper.master;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luolibing on 2019/3/14.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2180", 3000, event -> {});
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.submit(() -> {
                Worker worker1 = new Worker(zk, "worker-" + finalI);
                worker1.printAll();
            });
        }
    }
}
