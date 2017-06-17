package cn.tim.zookeeper.barries;

import org.apache.zookeeper.KeeperException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * User: luolibing
 * Date: 2017/6/6 15:08
 */
@RestController
@SpringBootApplication
public class SyncPrimitiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncPrimitiveApplication.class, args);
    }

    /**
     * barrier栅栏
     * @throws KeeperException
     * @throws InterruptedException
     */
    @RequestMapping(value = "/barrier/enter")
    public void enterBarrier() throws KeeperException, InterruptedException {
        SyncPrimitive.Barrier barrier = new SyncPrimitive.Barrier("127.0.0.1:2181", "/b1", 3);
        barrier.enter();
        System.out.println("process enterBarrier");
        barrier.leave();
    }

    @RequestMapping(value = "/queue/produce/{num}")
    public void produceQueue(@PathVariable Integer num) throws KeeperException, InterruptedException {
        SyncPrimitive.Queue queue = new SyncPrimitive.Queue("127.0.0.1:2181", "/q");
        queue.produce(num);
    }

    @RequestMapping(value = "/queue/consume")
    public Object consume() throws KeeperException, InterruptedException {
        SyncPrimitive.Queue queue = new SyncPrimitive.Queue("127.0.0.1:2181", "/q");
        return Collections.singletonMap("num", queue.consume());
    }



    @RequestMapping(value = "/highbarrier/enter")
    public void enterHighLevelBarrier() throws KeeperException, InterruptedException {
        HighLevelBarrier barrier = new HighLevelBarrier("127.0.0.1:2181", "/p", 3);
        barrier.enter();
        System.out.println("process enterHighLevelBarrier");
        barrier.leave();
    }
}
