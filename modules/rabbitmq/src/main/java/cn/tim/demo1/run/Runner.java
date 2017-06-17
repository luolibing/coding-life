package cn.tim.demo1.run;

import cn.tim.demo1.consumer.MyConsumer;
import cn.tim.demo1.producer.MyProducer;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 15/10/21.
 */
public class Runner {

    public void run() throws IOException, TimeoutException, InterruptedException {
        // 消费者
        MyConsumer consumer = new MyConsumer("queue");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        // 生产者
        MyProducer producer = new MyProducer("queue");

        for(int i = 0; i<10000; i++) {
            Map<String, String> message = new HashMap<>();
            message.put("message number", "message " + i);
            producer.sendMessage((Serializable) message);
            Thread.sleep(100);
        }
    }
}
