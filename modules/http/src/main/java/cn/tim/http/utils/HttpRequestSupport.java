package cn.tim.http.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.Objects;

/**
 * Created by LuoLiBing on 16/7/28.
 */
public class HttpRequestSupport {

    public static void parseRequest(HttpServletRequest request) {
        System.out.println();
        System.out.println(String.join(" ", request.getMethod(), request.getRequestURI(), request.getProtocol()));
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            System.out.println(String.join(": ", key, request.getHeader(key)));
        }
        // 空行
        System.out.println();
        // 空行下是请求体
        try(BufferedReader reader = request.getReader()) {
            String line;
            while (( line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        final Enumeration<String> attributeNames = request.getParameterNames();
        while (attributeNames.hasMoreElements()) {
            String key = attributeNames.nextElement();
            System.out.println(String.join("&", key + "=" + Objects.toString(request.getParameter(key))));
        }

        // 来源相关
        System.out.println("remoteUser= " + request.getRemoteUser());
        System.out.println("remoteHost= " + request.getRemoteHost());
        System.out.println("remoteAddr= " + request.getRemoteAddr());
        System.out.println("locale= " + request.getLocale());
    }

    public static <T> void parseResponse(String url, HttpMethod httpMethod, Class<T> clazz) {
        System.out.println();
        ResponseEntity<T> entity = new RestTemplate().exchange(url, httpMethod, HttpEntity.EMPTY, clazz);
        System.out.println(String.join(" ", "HTTP/1.1", entity.getStatusCode().value() + "", entity.getStatusCode().name()));
        HttpHeaders headers = entity.getHeaders();
        headers.forEach((key, values) -> System.out.println(String.join(": ", key, String.join(",", values))));
        // 空行,以下为responseBody
        System.out.println();
        System.out.println(entity.getBody());
    }
}
