package cn.tim.demo1.producer;

import cn.tim.demo1.endpoint.EndPoint;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 15/10/21.
 * 生产者
 */
public class MyProducer extends EndPoint {


    public MyProducer(String endpointName) throws IOException, TimeoutException {
        super(endpointName);
    }


    /**
     * 发送消息，
     * @param object
     */
    public void sendMessage(Serializable object) throws IOException {
        // 发布消息
        channel.basicPublish("", endPointName, null, SerializationUtils.serialize(object));
    }
}
