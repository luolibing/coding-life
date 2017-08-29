package redis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created by LuoLiBing on 16/2/29.
 */
public class Chapter01Test {

    private final static Logger logger = LoggerFactory.getLogger(Chapter01Test.class);

    @Test
    public void set() {
        Jedis jedis = new Jedis();
        jedis.set("name", "liuxiaoling");
        System.out.println(jedis.get("name"));
    }

    /**
     * 管道执行
     * 将多个命令发送到服务端，不需要逐个发送，逐个等待，在最后一个步骤中返回数据。
     * 如果返回的数据量很大，最好是将一批适量大小的指令发送到服务端，然后等待响应，超大量的数据可能会导致占用过多的内存。
     */
    @Test
    public void pipeline() {
        Jedis jedis = new Jedis();
        Pipeline pipelined = jedis.pipelined();
        IntStream.range(0, 100).forEach(index -> {
            pipelined.set(Integer.toString(index), Integer.toString(index * 10));
        });
        pipelined.sync();
    }


    /**
     * 订阅响应返回值：1 消息类型，订阅or取消订阅or消息, 2 订阅or取消订阅的频道 3 已订阅的个数
     */
    public JedisPubSub subscribe() {
        // 订阅消息
        JedisPubSub jedisPubSub = new JedisPubSub() {
            // 返回结果包括，1 类型onMessage中Message即表明类型 2 channel关联的通道 3 消息或者是当前订阅的总数
            @Override
            public void onMessage(String channel, String message) {
                logger.info("接收到消息: {}", message);
                messageCountLatch.countDown();
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                logger.info("订阅channel: {}", channel);
            }

            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                logger.info("取消订阅channel: {}", channel);
            }
        };

        new Thread(() ->{
            Jedis jedis = new Jedis();
            jedis.subscribe(jedisPubSub, "test_subscribe");
            logger.info("subscribe channel[test_subscribe]");
            jedis.quit();
        }).start();

        return jedisPubSub;
    }

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private CountDownLatch messageCountLatch = new CountDownLatch(3);

    public void setPublisher() {
        new Thread(() -> {

            try {
                logger.info("ready to connecting");
                countDownLatch.await();
                Jedis jedis = new Jedis();
                jedis.publish("test_subscribe", "hello, good first");
                Thread.sleep(1000);
                jedis.publish("test_subscribe", "hello, good second");
                Thread.sleep(1000);
                jedis.publish("test_subscribe", "hello, good fourth");
                Thread.sleep(1000);
                jedis.quit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Test
    public void executeSubPub() throws InterruptedException {
        setPublisher();
        JedisPubSub subscribe = subscribe();
        countDownLatch.countDown();
        messageCountLatch.await();
        // 最终取消订阅
        subscribe.unsubscribe();
    }

    @Test
    public void counter() {
        Jedis jedis = new Jedis();
        Random rand = new Random();
        Pipeline pipelined = jedis.pipelined();
        LongStream.range(0L, 1_000_000_000L)
                .forEach((index) ->{
                    boolean b = rand.nextBoolean();
                    jedis.setbit("gender", index, b);
                    if(index % 1000 == 0) {
                        pipelined.sync();
                    }
                });
    }

    @Test
    public void setGender() {
        Jedis jedis = new Jedis();
        jedis.setbit("gender", 1_000_000_000L, true);
    }

    @Test
    public void byteOp() {
        BitSet bitSet = new BitSet();
        bitSet.set(0);
        bitSet.set(3);
        // 1的个数
        System.out.println(bitSet.cardinality());
        // 最左不为0的位数
        System.out.println(bitSet.length());
        // 总共的位数
        System.out.println(bitSet.size());

        Jedis jedis = new Jedis();
        BitSet genderCount = BitSet.valueOf(jedis.get("gender".getBytes()));
        System.out.println(genderCount.cardinality());
    }

    /**
     * 事务
     */
    @Test
    public void multi() {
        Jedis jedis = new Jedis();
        // jedis的事务如果在入队时没报错，在提交事务时报错，并不会因为其中的某个指令出现错误，而导致所有的指令都回滚
        Transaction transaction = jedis.multi();
        transaction.set("a", "3");
        transaction.incr("a");
        transaction.exec();
    }

    @Test
    public void test() {
        Jedis jedis = new Jedis();
        String num = jedis.watch("num");

    }
}
