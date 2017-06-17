package cn.tim.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * Created by TIM on 2015/8/17.
 *
 */
@Component
public class MyHandler extends TextWebSocketHandler {

    private static TransferQueue<String> transferQueue = new LinkedTransferQueue<>();

    /*@Autowired
    private SimpMessagingTemplate messageTemplate;*/

    public MyHandler() {

        for(int i=1; i<100000; i++) {
            try {
                transferQueue.put("response" + i + ":" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // ...
        String msg = message.getPayload();
        session.sendMessage(new TextMessage("response:" + msg));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        while (true) {
            Thread.sleep(500);
            String msg = transferQueue.take();
            //messageTemplate.send(new GenericMessage(msg));
        }
    }
}
