import com.tim.webflux.GreetingHandler;
import com.tim.webflux.ReactiveApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author Kroos.luo
 * @since 2019-07-19 20:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReactiveApplication.class)
public class GreetingHandlerTest {

    @Test
    public void greetingTest() {
        Mono<ClientResponse> responseMono = WebClient.create("http://localhost:8080")
                .get()
                .uri("/hello")
                .exchange();
        responseMono.flatMap(res -> {
            System.out.println("aaaaa");
            return res.bodyToMono(String.class);
        })
        .flux()
        .subscribe(s -> System.out.println("receive response = " + s));
        System.out.println("async");
    }
}
