package com.tim.webflux;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @author Kroos.luo
 * @since 2019-07-19 19:56
 */
@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromObject(produceBody()));
    }

    public String produceBody() {
        System.out.println("request body");
        return "hello Mono";
    }
}
