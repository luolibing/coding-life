package cn.tim.demo2;

import cn.tim.demo1.endpoint.EndPoint;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

/**
 * Created by LuoLiBing on 15/10/23.
 */
public class Productor extends EndPoint {

    public Productor(String endpointName) throws IOException, TimeoutException {
        super(endpointName);
    }

    /**
     * 发送消息，
     * @param object
     */
    public void sendMessage(Serializable object) throws IOException {
        // 发布消息
        channel.basicPublish("", "hello", null, SerializationUtils.serialize(object));
    }
}
