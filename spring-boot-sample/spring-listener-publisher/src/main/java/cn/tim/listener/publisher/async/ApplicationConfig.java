package cn.tim.listener.publisher.async;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by luolibing on 2017/4/12.
 * TODO 能否修改成异步回调方式， 或者future方式
 */
@Configuration
public class ApplicationConfig implements ApplicationContextAware {

    /**
     * 设置了线程池，即可开启异步模式
     * @return
     */
    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster(applicationContext);
        int cores = Runtime.getRuntime().availableProcessors() + 1;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                cores,
                cores * 10,
                5_000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(cores * 100),
                new ThreadPoolExecutor.AbortPolicy()
        );
        eventMulticaster.setTaskExecutor(threadPool);
        eventMulticaster.setErrorHandler(t -> {

        });
        return eventMulticaster;
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
