package cn.tim.zookeeper.highlevel;

import cn.tim.zookeeper.highlevel.lock.ReadWriteLock;
import cn.tim.zookeeper.highlevel.lock.RevocableLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Zookeeper高级应用
 * 1 Name Service(命名服务，注册中心)
 * 2 Configuration(配置中心)
 * 3 Group Membership(leader选举)
 *
 * 全局锁：
 * 1 读写锁
 * 2 可撤销锁
 * User: luolibing
 * Date: 2017/6/6 16:57
 */
@RestController
@SpringBootApplication
public class ZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }

    private ReadWriteLock lock = new ReadWriteLock("127.0.0.1:2181", "/readWriteLock");

    private RevocableLock revocableLock = new RevocableLock("127.0.0.1:2181", "/revocableLock");

    @GetMapping("/readLock")
    public void readLock() {
        lock.tryReadLock();
    }

    @GetMapping("/writeLock")
    public void writeLock() {
        lock.tryWriteLock();
    }

    @GetMapping("/revocableLock/lock")
    public void revocableLock() {
        revocableLock.tryLock(UUID.randomUUID().toString());
    }

    @GetMapping("/revocableLock/release")
    public void releaseRevocableLock() {
        revocableLock.tryRevoceLock();
    }
}
