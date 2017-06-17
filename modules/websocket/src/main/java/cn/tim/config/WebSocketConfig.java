package cn.tim.config;

import cn.tim.handler.MyHandler;
import cn.tim.handshake.MyHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * Created by TIM on 2015/8/17.
<<<<<<< HEAD
 * websocket
=======
 * websocket配置  也可以使用xml进行配置
>>>>>>> 4860438266b03b4078769e517e128c6fb0e79800
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MyHandler webSocketHandler;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/log");//.withSockJS();
                //addInterceptors(new MyHandshakeInterceptor());
                //.withSockJS();
    }

    /*@Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }*/

    /**
<<<<<<< HEAD
=======
     * 配置websocket 配置, 也可以放到xml中配置
>>>>>>> 4860438266b03b4078769e517e128c6fb0e79800
     * @return
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
}
