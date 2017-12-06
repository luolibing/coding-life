package cn.tim.session.redis;

import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServerPortInfoApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by LuoLiBing on 16/1/15.
 */
public class HelloControllerTest {

    @Test
    public void sessionExpiry() throws InterruptedException {

        String port = null;
        final ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(Application.class)
                .properties("server.port:8080")
                .initializers(new ServerPortInfoApplicationContextInitializer())
                .run();
        port = context.getEnvironment().getProperty("local.server.port");

        final URI uri = URI.create("http://localhost:" + port + "/uid");
        RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        String uuid1 = response.getBody();
        System.out.println(uuid1);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Cookie", response.getHeaders().getFirst("Set-Cookie"));

        RequestEntity<Void> request = new RequestEntity<Void>(requestHeaders,
                HttpMethod.GET, uri);

        String uuid2 = restTemplate.exchange(request, String.class).getBody();
        System.out.println(uuid2);
        Thread.sleep(5000);

        String uuid3 = restTemplate.exchange(request, String.class).getBody();
        System.out.println(uuid3);
    }
}
