package cn.tim.starter;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/1/29.
 */
@SpringBootApplication
@Controller
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private BaseInterface baseInterface;

    @RequestMapping(value = "/")
    public String index() {
        return "forward: index.html";
    }

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(String... strings) throws Exception {
        rest();
        System.out.println(baseInterface.getClass());
    }

    public void rest() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("touser", "liyongfeng3");
        paramMap.put("msgtype", "text");
        paramMap.put("agentid", 0);
        paramMap.put("text", Collections.singletonMap("content", "截止到今天4，您已收到10条系统消息，请及时去Man端导航上的小信封查看。"));
        paramMap.put("safe", 0);
        Map responseEntity = restTemplate.postForObject("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ancGK1D7df89xjw08RNyQ54NGSaFxlwh4Yor6xbVA889tR17SlwPWlklNrcqlNuQ",
                paramMap, Map.class);
        System.out.println(responseEntity);
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(1000 * 2);
        factory.setConnectTimeout(1000 * 2);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(100)
                // 默认的DefaultHttpRequestRetryHandler重试次数为3
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, false))
                .build();
        factory.setHttpClient(httpClient);
        return new RestTemplate(factory);
    }
}
