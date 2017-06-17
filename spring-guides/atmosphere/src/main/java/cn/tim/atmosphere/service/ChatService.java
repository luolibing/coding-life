package cn.tim.atmosphere.service;

import cn.tim.atmosphere.entity.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.config.managed.Decoder;
import org.atmosphere.config.managed.Encoder;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by LuoLiBing on 16/1/19.
 * 聊天服务类
 * 前端发送一个消息,这个消息会播给所有的其他客户端
 * 一个client断开之后,会通知给其他所有的客户端
 */
@ManagedService(path = "/chat")
public class ChatService {

    private final static Logger logger = LoggerFactory.getLogger(ChatService.class);

    /**
     * 建立连接
     * @param resource
     */
    @Ready
    public void onReady(final AtmosphereResource resource) {
        logger.info("Connected ready!", resource.uuid());
    }

    /**
     * 断开连接,主动调用并且响应给所有的客户端
     * @param event
     */
    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
        logger.info("client {} disconnect[{}]", event.getResource().uuid(),
                event.isCancelled() ? "cancelled": "closed");
    }

    /**
     * 发送消息,并且经过编码与解码
     * @param message
     * @return
     */
    @Message(encoders = JacksonEncoderDecoder.class, decoders = JacksonEncoderDecoder.class)
    public ChatMessage onMessage(ChatMessage message) {
        logger.info("author {} sent message {}", message.getAuthor(), message.getMessage());
        return message;
    }


    public static class JacksonEncoderDecoder
            implements Encoder<ChatMessage, String>, Decoder<String, ChatMessage> {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public String encode(ChatMessage m) {
            try {
                return this.mapper.writeValueAsString(m);
            }
            catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        }

        @Override
        public ChatMessage decode(String s) {
            try {
                return this.mapper.readValue(s, ChatMessage.class);
            }
            catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        }

    }
}
