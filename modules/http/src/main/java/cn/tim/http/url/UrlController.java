package cn.tim.http.url;

import cn.tim.http.utils.HttpRequestSupport;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/7/27.
 */
@RequestMapping(value = "/client")
@RestController
public class UrlController {

    /**
     * uri资源标识符包括 http://user:pass@www.baidu.com:80/dir/index?uid=1#ch1
     * url统一资源定位符,只是对资源的一个简单的地址
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @RequestMapping(value = "define")
    public Object define(HttpServletRequest request) throws URISyntaxException {
        final String requestURI = request.getRequestURI();
        final StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURI);
        System.out.println(requestURL);
        URI uri = new URI("http", "local", "/");
        return ResponseEntity.ok().build();
    }

    /**
     * 获取请求头
     * GET /uri/http HTTP/1.1
     host: localhost:8080
     connection: keep-alive
     accept: html/txt
     cache-control: no-cache
     user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36
     postman-token: 4877dec1-52a6-aeab-5217-dfb4b6e4d535
     accept-encoding: gzip, deflate, sdch
     accept-language: zh-CN,zh;q=0.8,en;q=0.6
     // 空行
     accsss_token=admin
     * @param request
     * @return
     */
    @RequestMapping(value = "/http", method = {RequestMethod.GET, RequestMethod.POST})
    public Object http(HttpServletRequest request) {
        HttpRequestSupport.parseRequest(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 响应体
     * HTTP/1.1 200 OK
     Date: Thu, 28 Jul 2016 02:47:43 GMT
     Content-Type: text/html; charset=utf-8
     Transfer-Encoding: chunked
     Connection: Keep-Alive
     Vary: Accept-Encoding
     Set-Cookie: BAIDUID=F7EE352CEE7828601E5C05879FCE5740:FG=1; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com,BIDUPSID=F7EE352CEE7828601E5C05879FCE5740; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com,PSTM=1469674063; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com,BDSVRTM=0; path=/,BD_HOME=0; path=/,H_PS_PSSID=1443_18241_17943_15475_11870_20705_20696; path=/; domain=.baidu.com
     P3P: CP=" OTI DSP COR IVA OUR IND COM "
     Cache-Control: private
     Cxy_all: baidu+d6f3f167146359fb185085bbb3ec3e32
     Expires: Thu, 28 Jul 2016 02:47:39 GMT
     X-Powered-By: HPHP
     Server: BWS/1.1
     X-UA-Compatible: IE=Edge,chrome=1
     BDPAGETYPE: 1
     BDQID: 0x8495e731000739f7
     BDUSERID: 0


     * @return
     */
    @RequestMapping(value = "post", method = RequestMethod.GET)
    public Object post() {
        HttpRequestSupport.parseResponse("http://www.baidu.com", HttpMethod.GET, String.class);
        return ResponseEntity.ok().build();
    }

    /**
     * get      获取资源
     * post     传输实体主体
     * put      传输文件
     * head     head只是返回请求头,不返回主体部分
     * options  用来询问指定uri指定资源支持的方法
     *
     *
     * @return
     */
    @RequestMapping(value = "get", method = {RequestMethod.GET, RequestMethod.HEAD})
    public Object get() {
        HttpRequestSupport.parseResponse("http://localhost:8080/server/get/1", HttpMethod.HEAD, Map.class);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "trace", method = RequestMethod.GET)
    public Object trace() {
//        HttpRequestSupport.parseResponse("http://localhost:8080/server/trace", HttpMethod.TRACE, String.class);
        final ResponseEntity<String> exchange = new RestTemplate().exchange("http://localhost:8080/server/trace", HttpMethod.TRACE, HttpEntity.EMPTY, String.class);
        System.out.println(exchange);
        return ResponseEntity.ok().build();
    }

    /**
     * connection: keep-alive   持久连接,节省多次建立连接与关闭
     * 管线化: pipelining 不用等待响应直接发送下一个请求
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }

    /**
     * 添加cookie会在response响应头中添加Set-Cookie: name=luolibing
     * Set-Cookie: name=luolibing
     * 下次访问的时候,会自动带上保存在客户端的cookie
     * @return
     */
    @RequestMapping(value = "cookie", method = RequestMethod.GET)
    public Object cookie() {
        HttpRequestSupport.parseResponse("http://localhost:8080/server/cookie", HttpMethod.GET, String.class);
        return ResponseEntity.ok().build();
    }


    /*********** 提升传输速度 *************/
    /**
     * 压缩传输内容编码
     * gizp compress deflate identity
     * @return
     */
    @RequestMapping(value = "encoding", method = RequestMethod.GET)
    public Object encoding() {

        return ResponseEntity.ok().build();
    }

    /**
     * 将实体主体分成多个块,每一块都用十六进制来标记块的大小,最后一块用0来标记
     * Transfer-Encoding: chunked
     * @return
     */
    @RequestMapping(value = "chunk", method = RequestMethod.GET)
    public Object chunk() {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "multipart", method = RequestMethod.POST)
    public Object multipart(HttpServletRequest request) throws IOException {
        MultiValueMap<String, Object> parts =
                new LinkedMultiValueMap<String, Object>();
        parts.add("file", new ByteArrayResource(Files.readAllBytes(Paths.get("/share/rms_resource.log"))));
        parts.add("filename", "rms_resource.log");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(parts, headers);

        // file upload path on destination server
        parts.add("destination", "./");

        ResponseEntity<String> response =
                restTemplate.exchange("http://localhost:8080/server/multipart",
                        HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }
        return ResponseEntity.ok().build();
    }

    private URI createMultipartURI() {
        return UriComponentsBuilder
                .fromUriString("http://localhost:8080")
                .path("/server/multipart")
                .build()
                .toUri();
    }

    /**
     * 判断获取
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "byteranges", method = RequestMethod.GET)
    public void byteRanges(HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
        response.addHeader("Content-Range", "bytes 500-1000/1000");
        //headers.add("Range", "bytes=500-");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                "http://192.168.0.111:8060/repository/default/%E8%B5%84%E6%BA%90/%E5%9B%BE%E7%89%87/315/%E5%AF%B9%E8%AF%9DAnd%20not%20only%20did%20I%20pay%20for%20your%20check,%20I%20also%20brought%20you%20another%20one.%E9%87%91%E8%A3%85%E5%BE%8B%E5%B8%88S05E05_Louis%20Litt.jpg",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                byte[].class);
        if(responseEntity.getStatusCode().value() == 200) {
            response.getOutputStream().write(responseEntity.getBody());
        }
    }

    /**
     * Accept内容协商
     *
     * @return
     */
    @RequestMapping(value = "accept")
    public Object accept() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        ResponseEntity<String> exchange = new RestTemplate().exchange("http://www.baidu.com/", HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        System.out.println(exchange);
        return ResponseEntity.ok().build();
    }
}
