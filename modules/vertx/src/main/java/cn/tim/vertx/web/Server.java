package cn.tim.vertx.web;

import io.vertx.core.AbstractVerticle;

/**
 * Created by LuoLiBing on 16/12/1.
 */
public class Server extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req ->
         req.response()
            .putHeader("content-type", "text/plain")
            .end("Hello from Vert.x")
        ).listen(8080);
    }

    public static void main(String[] args) throws Exception {
        Server s = new Server();
        s.start();
    }
}
