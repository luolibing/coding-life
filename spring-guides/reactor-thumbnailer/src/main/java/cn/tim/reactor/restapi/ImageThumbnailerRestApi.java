package cn.tim.reactor.restapi;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.function.Consumer;
import reactor.net.NetChannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by LuoLiBing on 15/11/4.
 */
public class ImageThumbnailerRestApi {

    public final static String IMG_THUMBNAIL_URI = "/image/thumbnail.jpg";

    public final static String THUMBNAIL_REQ_URI = "/thumbnail";

    /**
     * 收到一个图片上传的请求，交给reactor的一个消费者进行处理
     * @param channel
     * @param thumbnail
     * @param reactor
     * @return
     */
    public static Consumer<FullHttpRequest> thumbnailImage(NetChannel<FullHttpRequest, FullHttpResponse> channel,
                                                           AtomicReference<Path> thumbnail,
                                                           Reactor reactor) {
        return req -> {
            // 判断是否是post请求
            if(req.getMethod() != HttpMethod.POST) {
                channel.send(badRequest("错误的请求,请使用post方式请求"));
                return;
            }

            // 将post上来的文件保存到一个临时文件
            Path imageIn = null;
            try {
                imageIn = readUpload(req.content());
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }

            // 发送并且用一个消费者进行接收
            reactor.sendAndReceive("thumbnail", Event.wrap(imageIn), ev -> {
                thumbnail.set((Path) ev.getData());
                // 301跳转
                channel.send(redirect());
            });
        };
    }


    /**
     * get请求，查看缩略图
     * @param channel
     * @param thumbnail
     * @return
     */
    public static Consumer<FullHttpRequest> serveThumbnailImage(NetChannel<FullHttpRequest, FullHttpResponse> channel,
                                                                AtomicReference<Path> thumbnail) {
        System.out.println(thumbnail);
        return req -> {
          if(req.getMethod() != HttpMethod.GET) {
              channel.send(badRequest("请使用get方式请求"));
          } else {
              try {
                  channel.send(serveImage(thumbnail.get()));
              } catch (IOException e) {
                  throw new IllegalStateException(e.getMessage(), e);
              }
          }
        };
    }

    /*
     * 创建出一个400的响应
     */
    public static FullHttpResponse badRequest(String msg) {
        DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST);
        resp.content().writeBytes(msg.getBytes());
        resp.headers().set(CONTENT_TYPE, "text/plain");
        resp.headers().set(CONTENT_LENGTH, resp.content().readableBytes());
        return resp;
    }

    /**
     * 读取上传的图片到临时文件
     * @param content
     * @return
     * @throws IOException
     */
    private static Path readUpload(ByteBuf content) throws IOException {
        byte[] bytes = new byte[content.readableBytes()];
        content.readBytes(bytes);
        content.release();

        // 创建出临时文件
        Path imgIn = Files.createTempFile("upload", ".jpg");
        Files.write(imgIn, bytes);
        // 当VM退出时，删除临时文件
        imgIn.toFile().deleteOnExit();
        return imgIn;
    }


    public static FullHttpResponse redirect() {
        DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.MOVED_PERMANENTLY);
        resp.headers().set(CONTENT_LENGTH, 0);
        resp.headers().set(LOCATION, IMG_THUMBNAIL_URI);
        return resp;
    }


    public static FullHttpResponse serveImage(Path path) throws IOException {
        DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HTTP_1_1, OK);

        RandomAccessFile f = new RandomAccessFile(path.toString(), "r");
        resp.headers().set(CONTENT_TYPE, "image/jpeg");
        resp.headers().set(CONTENT_LENGTH, f.length());

        byte[] bytes = Files.readAllBytes(path);
        resp.content().writeBytes(bytes);

        return resp;
    }

    public static reactor.function.Consumer<Throwable> errorHandler(NetChannel<FullHttpRequest, FullHttpResponse> ch) {
        return ev -> {
            DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.INTERNAL_SERVER_ERROR);
            resp.content().writeBytes(ev.getMessage().getBytes());
            resp.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
            resp.headers().set(HttpHeaders.Names.CONTENT_LENGTH, resp.content().readableBytes());
            ch.send(resp);
        };
    }
}
