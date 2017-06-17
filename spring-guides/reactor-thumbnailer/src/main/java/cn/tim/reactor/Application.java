package cn.tim.reactor;

import cn.tim.reactor.restapi.ImageThumbnailerRestApi;
import cn.tim.reactor.thumbnailer.BufferedImageThumbnailer;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.composable.Stream;
import reactor.core.spec.Reactors;
import reactor.net.NetServer;
import reactor.net.config.ServerSocketOptions;
import reactor.net.netty.NettyServerSocketOptions;
import reactor.net.netty.tcp.NettyTcpServer;
import reactor.net.tcp.spec.TcpServerSpec;
import reactor.spring.context.config.EnableReactor;

import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static reactor.event.selector.Selectors.$;

/**
 * Created by LuoLiBing on 15/11/4.
 * 缩略图
 */
@EnableReactor
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
        closeLatch.await();
    }

    @Bean
    public Reactor reactor(Environment env) {
        Reactor reactor = Reactors.reactor(env, Environment.THREAD_POOL);
        reactor.receive($("thumbnail"), new BufferedImageThumbnailer(50));
        return reactor;
    }

    @Bean
    public ServerSocketOptions serverSocketOptions() {
        return new NettyServerSocketOptions().pipelineConfigurer(pipeline -> pipeline.addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(16 * 1024 * 1024)));
    }


    @Bean
    public NetServer<FullHttpRequest, FullHttpResponse> restApi(Environment env,
                                                                ServerSocketOptions opts,
                                                                Reactor reactor,
                                                                CountDownLatch closeLatch) throws InterruptedException {
        AtomicReference<Path> thumbnail = new AtomicReference<>();

        NetServer<FullHttpRequest, FullHttpResponse> server = new TcpServerSpec<FullHttpRequest, FullHttpResponse>(
                NettyTcpServer.class)
                .env(env).dispatcher("sync").options(opts)
                .consume(ch -> {
                    // filter requests by URI via the input Stream
                    Stream<FullHttpRequest> in = ch.in();

                    // serve image thumbnail to browser
                    in.filter((FullHttpRequest req) -> ImageThumbnailerRestApi.IMG_THUMBNAIL_URI.equals(req.getUri()))
                            .when(Throwable.class, ImageThumbnailerRestApi.errorHandler(ch))
                            .consume(ImageThumbnailerRestApi.serveThumbnailImage(ch, thumbnail));

                    // take uploaded data and thumbnail it
                    in.filter((FullHttpRequest req) -> ImageThumbnailerRestApi.THUMBNAIL_REQ_URI.equals(req.getUri()))
                            .when(Throwable.class, ImageThumbnailerRestApi.errorHandler(ch))
                            .consume(ImageThumbnailerRestApi.thumbnailImage(ch, thumbnail, reactor));

                    // shutdown this demo app
                    in.filter((FullHttpRequest req) -> "/shutdown".equals(req.getUri()))
                            .consume(req -> closeLatch.countDown());
                })
                .get();

        server.start().await();

        return server;
    }

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }


}
