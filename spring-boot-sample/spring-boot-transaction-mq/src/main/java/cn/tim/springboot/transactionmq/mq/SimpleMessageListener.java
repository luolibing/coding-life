package cn.tim.springboot.transactionmq.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luolibing on 2017/6/16.
 */
//@Component
public class SimpleMessageListener implements MessageListener {

    private final Map<Class, Consumer> consumerMap;

    public SimpleMessageListener(List<Consumer> consumers) {

        Map<Class, Consumer> beanMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(consumers)) {
            for(Consumer consumer : consumers) {
                beanMap.put(consumer.supportMessageClazz(), consumer);
            }
        }
        consumerMap = Collections.unmodifiableMap(beanMap);

    }

    @Override
    public void onMessage(Message message) {
        byte[] data = message.getBody();
        try {
            Object messageObj = deserialize(data);
            Consumer consumer = consumerMap.get(messageObj.getClass());
            if(consumer == null) {
                // printConsumer.handleMessage(messageObj);
            } else {
                consumer.handleMessage(messageObj);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

}
