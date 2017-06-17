package cn.tim.zookeeper.leaderelection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO zookeeper选举的实现
 * User: luolibing
 * Date: 2017/6/8 16:57
 */
@SpringBootApplication
public class LeaderElectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaderElectionApplication.class, args);
    }
}
