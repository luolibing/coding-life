package cn.tim.redis.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Created by LuoLiBing on 15/11/9.
 */
public class Receiver {

    private final static Logger logger = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch;

    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * MessageListenerAdapter 适配器默认适配调用的方法
     * @param message
     */
    public void receiveMessage(Object message) {
        logger.info("收到消息<" + message + ">");
        latch.countDown();
    }
}
