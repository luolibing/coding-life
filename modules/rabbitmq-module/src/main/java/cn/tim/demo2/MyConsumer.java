package cn.tim.demo2;

import cn.tim.demo1.endpoint.EndPoint;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 15/10/23.
 */
public class MyConsumer extends EndPoint implements Runnable, Consumer {

    public MyConsumer(String endpointName) throws IOException, TimeoutException {
        super(endpointName);
    }

    /**
     * 接收消息，以二进制的方式传输，收到二进制内容，然后转换为目标对象
     * @param s
     * @param envelope
     * @param basicProperties
     * @param bytes
     * @throws IOException
     */
    @Override
    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        HashMap resultMap = (HashMap) SerializationUtils.deserialize(bytes);
        System.out.println("Message Number " + endPointName + ":" + resultMap.get("message number") + " received." );
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            // 消费者
            channel.basicConsume(endPointName, true, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 消费者注册的时候调用
     * @param consumerTag
     */
    @Override
    public void handleConsumeOk(String consumerTag) {
        System.out.println("consumerTag:" + consumerTag);
    }

    @Override
    public void handleCancelOk(String s) {
        System.out.println(s);
    }

    @Override
    public void handleCancel(String s) throws IOException {
        System.out.println(s);
    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {
        System.out.println(s);
    }

    @Override
    public void handleRecoverOk(String s) {
        System.out.println(s);
    }
}
