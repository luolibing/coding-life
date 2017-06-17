package cn.tim.http.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by LuoLiBing on 16/8/2.
 * 状态码
 * 1XX  信息状态码,接收的请求正在处理
 * 2XX  成功状态码,请求正常处理完毕
 * 3XX  重定向状态码,需要进行附加操作以完成请求
 * 4XX  客户端错误状态码,服务器无法处理请求
 * 5XX  服务器错误状态码,服务器处理请求出错
 *
 */
@RestController
@RequestMapping(value = "/response")
public class ResponseStatusController {

    /**
     * 101协议转换
     * @return
     */
    @RequestMapping(value = "switch")
    public Object switchPro() {
        return ResponseEntity.status(HttpStatus.SWITCHING_PROTOCOLS).header("Connection", "Upgrade").header("Upgrade", "websocket").build();
    }

    @RequestMapping(value = "ok")
    public Object ok() {
        return ResponseEntity.ok().build();
    }

    /**
     * 204没有内容可返回,一般在只需要从客户端向服务器发送消息,而对客户端不需要发送新消息内容的情况下使用
     * @return
     */
    @RequestMapping(value = "noContent")
    public Object noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 206返回部分内容
     * @return
     */
    @RequestMapping(value = "partialContent")
    public Object partialContent() {
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).header("Content-Range", "bytes 10-20/20").body("11111111111111111111111111111111111111111111111111111111111111");
    }

    /**
     * 301永久性重定向.该状态码表示请求的资源已被分配了新的URI,以后使用
     * @param response
     */
    @RequestMapping(value = "redirect1")
    public void redirect1(HttpServletResponse response) {
        response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
        response.addHeader("Location", "http://www.baidu.com");
    }

    /**
     * 302临时重定向
     * 303状态码明确表示客户端应当采用GET方法获取资源
     * @param response
     */
    @RequestMapping(value = "redirect2")
    public void redirect2(HttpServletResponse response) {
        response.setStatus(HttpStatus.FOUND.value());
        response.addHeader("Location", "http://www.baidu.com");
    }

    /**
     * 304 not modified 没有修改,不需要更新
     * 当收到客户端发送的附带条件的请求时,服务器会根据情况返回304
     * 例如 If-Match If-Modified-Since  If-None-Match
     * @param response
     */
    @RequestMapping(value = "redirect4")
    public void redirect4(HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_MODIFIED.value());
    }

    /**
     * 临时重定向
     * @param response
     */
    @RequestMapping(value = "redirect7")
    public void redirect7(HttpServletResponse response) {
        response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
        response.addHeader("Location", "http://www.baidu.com");
    }

    /**
     * 4XX客户端错误
     * 表示请求报文中存在语法错误,无法解析
     */
    @RequestMapping(value = "clientError")
    public Object clientError0() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 401 Basic 认证
     * 通过请求头中添加 authorization: Basic token  对用户名和密码user:pass 进行base64编码之后进行传输
     * 当需要401认证的时候返回状态码401,同时添加响应头 WWW-Authenticate: Basic realm="security system"
     * @param request
     * @return
     */
    @RequestMapping(value = "clientError1")
    public Object unauthorized(HttpServletRequest request) {
        String authorization = request.getHeader("authorization");
        if(StringUtils.isEmpty(authorization)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"Tomcat Manager Application\"").build();
        }
        String username = new String(Base64.getDecoder().decode(authorization.split(" ")[1]), StandardCharsets.UTF_8);
        System.out.println(username);
        if(Objects.equals(username, "admin:admin")) {
            return Collections.singletonMap("success","Y");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("WWW-Authenticate", "Basic realm=\"Tomcat Manager Application\"").build();
        }
    }

    @RequestMapping(value = "clientError3")
    public Object forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
