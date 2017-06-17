package cn.tim.http.header;

import cn.tim.http.server.Person;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

/**
 * Created by LuoLiBing on 16/8/4.
 */
@RestController
@RequestMapping(value = "header")
public class HeaderController {

    @RequestMapping(value = "cacheControl")
    public void cache(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "public, max-age=10000");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(
                Files.readAllBytes(Paths.get("/Users/tim/Downloads/How's the weather视频对话_woman1.jpg")));
        outputStream.close();
    }

    /**
     * connection的作用
     * 控制不再转发给代理的首部字段
     * 管理持久连接
     *
     */
    @RequestMapping(value = "connection1")
    public Object connection1() {
        return ResponseEntity.status(HttpStatus.SWITCHING_PROTOCOLS).header("Connection", "Upgrade").header("Upgrade", "websocket").build();
    }

    @RequestMapping(value = "connection2")
    public Object connection2() {
        // header("Connection", "close")
        return ResponseEntity.ok().header("Connection", "Keep-Alive").header("Keep-Alive", "timeout=10000,max=50000").build();
    }

    /**
     * 无缓存
     * Cache-Control: no-cache
     * Pragma: no-cache
     * @return
     */
    @RequestMapping(value = "pragma")
    public Object pragma() {
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache")
                .header("Pragma", "no-cache")
                .build();
    }

    /**
     * 在报文主体后记录了哪些首部字段,在分块传输编码时使用.
     * @return
     */
    @RequestMapping(value = "trailer")
    public Object trailer() {
        return ResponseEntity.ok()
                .header("Trailer", "Expire")
                .header("Expire", DateTimeFormatter.BASIC_ISO_DATE.format(LocalDateTime.now()))
                .body(Collections.singletonMap("success", "Y"));
    }

    @RequestMapping(value = "transferEncoding")
    public Object transferEncoding() throws IOException {
        return ResponseEntity.ok()
                .header("Transfer-Encoding", "chunked")
                .body(Collections.singletonMap("content", new String(Files.readAllBytes(Paths.get("/share/rms_resource.log")), StandardCharsets.UTF_8)));
    }

    /**
     * 请求头
     * Upgrade: TLS/1.0
     * Connection: Upgrade
     *
     * @return
     */
    @RequestMapping(value = "upgrade")
    public Object upgrade() {
        return ResponseEntity
                .status(HttpStatus.SWITCHING_PROTOCOLS)
                .header("Upgrade", "TLS/1.0, Http/1.1")
                .header("Connection", "Upgrade")
                .build();
    }

    /**
     * Accept首部字段通知服务器,用户代理能够处理的媒体类型及媒体类型的相对优先级
     * 文本文件
     * text/html, text/plain
     * application/xhtml+xml, application/xml
     *
     * 图片文件
     * image/jpeg, image/gif, image/png
     *
     * 视频文件
     * video/mpeg, video/quicktime
     *
     * 二进制文件
     * application.octet-stream, application/zip
     * @return
     */
    @RequestMapping(value = "accept")
    public void accept() {

    }

    /**
     * 客户端期望服务器出现某种行为
     * @return
     */
    @RequestMapping(value = "expect")
    public Object expect() {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "ifMatch")
    public Object ifMatch() {
        return ResponseEntity.ok().header("ETag", "12345").build();
    }

    /**
     * 告知服务器请求的原始资源的URI
     * @return
     */
    @RequestMapping(value = "referer")
    public Object referer() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "http://localhost:8060");
        ResponseEntity<Person> exchange = new RestTemplate().exchange("http://localhost:8080/server/get/1", HttpMethod.GET, new HttpEntity<>(headers), Person.class);
        System.out.println(exchange);
        return ResponseEntity.ok().build();
    }

    /**
     * allow: 允许那些方法方式调用,对于不支持的,调用报405方法不支持错误
     * @return
     */
    @RequestMapping(value = "allow")
    public Object allow() {
        return ResponseEntity.ok().header("Allow", "GET, HEAD").build();
    }

    /**
     * 告知客户端资源的失效日期
     * @return
     */
    @RequestMapping(value = "expires")
    public Object expires() {
        return ResponseEntity.ok().header("Expires", DateTimeFormatter.ISO_DATE.format(LocalDateTime.now())).build();
    }

    /**
     * 告知客户端资源的最后修改时间
     * @return
     */
    @RequestMapping(value = "lastModified")
    public Object lastModified() {
        return ResponseEntity.ok().header("Last-Modified", DateTimeFormatter.ISO_DATE.format(LocalDateTime.now())).build();
    }

    /**
     * 当向response添加cookie时,响应头中会添加一个set-cookie字段,告知客户端cookie的一些信息,进行保存
     * 当下次访问的时候,会携带这些cookie: status=enable 进行访问
     * Set-Cookie: status=enable; expires=Tue,05kfkdsf; path=/; domain=tim.cn; HttpOnly: true (禁止javascript访问); Secure: 仅在https安全通信时才会发送cookie
     * Cookie: status=enable
     * @param response
     */
    @RequestMapping(value = "cookie")
    public void cookie(HttpServletResponse response) {
        final Cookie cookie = new Cookie("status", "enable");
        cookie.setHttpOnly(true);
//        cookie.setDomain("localhost:8080");
//        cookie.setPath("/header");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
}
