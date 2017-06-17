package cn.tim.springboot.client;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * restTemplate设置
 * 例如超时时间，最大连接数，重试机制等
 * User: luolibing
 * Date: 2017/5/16 17:21
 */
@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(1000 * 5);
        factory.setConnectTimeout(1000 * 10);
        CloseableHttpClient httpClient = HttpClients.custom()
                // .disableAutomaticRetries() 关闭自动重试
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(100)
                // 默认的DefaultHttpRequestRetryHandler重试次数为3
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true) {
                    @Override
                    public boolean retryRequest(IOException e, int executionCount, HttpContext context) {
                        System.out.println("retry [" + context + "] retry count=" + executionCount);
                        // 超过次数退出
                        if(executionCount > this.getRetryCount()) {
                            return false;
                        }

                        // 未知主机，连接超时继续
                        if (e instanceof UnknownHostException || e instanceof ConnectTimeoutException ) {
                            // e instanceof SocketTimeoutException
                            return true;
                        }

                        // 其他，默认get/put方法为幂等，其他非幂等不继续
                        HttpClientContext clientContext = HttpClientContext.adapt(context);
                        HttpRequest request = clientContext.getRequest();
                        String method = request.getRequestLine().getMethod();
                        switch (method) {
                            // 默认get, put为幂等方法
                            case "GET":
                            case "PUT": return true;
                            // 其他为非幂等
                            default: return false;
                        }

                    }
                })
                .build();
        factory.setHttpClient(httpClient);
        return new RestTemplate(factory);
    }


    @Primary
    @Bean
    public RestTemplate restTemplate1() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(1000 * 5);
        factory.setConnectTimeout(1000 * 10);
        CloseableHttpClient httpClient = HttpClients.custom()
                // .disableAutomaticRetries() 关闭自动重试
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(100)
                // 默认的DefaultHttpRequestRetryHandler重试次数为3
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true) {
                    @Override
                    public boolean retryRequest(IOException e, int executionCount, HttpContext context) {
                        if (e instanceof UnknownHostException || e instanceof ConnectTimeoutException
                                || e instanceof SocketTimeoutException) {
                            throw new NetworkException("网络异常");
                        }
                        // 不重试
                        return false;
                    }
                })
                .build();
        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        // 默认是400，或者500抛出异常异常
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                InputStream in = response.getBody();
                StringWriter writer = new StringWriter();
                IOUtils.copy(in, writer);
                throw new NetworkException(writer.toString());
            }
        });
        return restTemplate;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(String... strings) throws Exception {
        HashMap resultMap = restTemplate.getForObject(createClientURI(), HashMap.class);
        System.out.println(resultMap);
    }

    private URI createClientURI() {
        return UriComponentsBuilder
                .fromUriString("http://localhost:8080")
                .path("/hello")
                .build()
                .toUri();
    }
}
