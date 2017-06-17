package cn.tim.reactor.receiver;

import cn.tim.reactor.entity.QuoteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.util.concurrent.CountDownLatch;

/**
 * Created by LuoLiBing on 15/10/26.
 * 消费者
 */
@Service
public class Receiver implements Consumer<Event<?>> {


    /**
     * 接收event进行处理
     * @param event
     */
    @Override
    public void accept(Event<?> event) {
        QuoteResource quoteResource
                = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", QuoteResource.class);
        System.out.println("Quote " + event.getData() + ": " + quoteResource.getValue().getQuote());
        // 将锁存器countDown进行countDown操作
        latch.countDown();
    }

    @Autowired
    CountDownLatch latch;

    RestTemplate restTemplate = new RestTemplate();

}
