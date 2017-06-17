package cn.tim.spring4;

import cn.tim.spring4.custom.LifecycleProcessorTest;
import cn.tim.spring4.ioc722.ClientService;
import cn.tim.spring4.ioc722.CommandManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.LifecycleProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.DefaultLifecycleProcessor;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by LuoLiBing on 16/5/17.
 * spring 4.0新功能
 * 一 4.0全面支持java8功能
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 定义多个别名
     * 定义bean的方式:
     * 1 典型的容器直接调用指定的构造函数进行new操作,使用默认的构造方法得需要一个无参的构造函数
     * 2 使用静态工厂方法来进行创建对象
     * 3 内部静态类表示方式,com.example.Foo$Bar
     */
    @Bean(name = {"restTemplate", "myAppA-restTemplate", "myAppB-restTemplate"})
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private ClientService clientService;

    private CommandManager commandManager;

    @Autowired
    private LifecycleProcessorTest lifecycleProcessorTest;

    @Inject
    public void setCommandManager(Provider<CommandManager> provider) {
        this.commandManager = provider.get();
        commandManager.process("start");
    }

    @Bean
    public LifecycleProcessor lifecycleProcessor() {
        DefaultLifecycleProcessor lifecycleProcessor = new DefaultLifecycleProcessor();
        lifecycleProcessor.setTimeoutPerShutdownPhase(1000 * 10L);
        return lifecycleProcessor;
    }

    @Override
    public void run(String... args) throws Exception {
        commandManager.process("start");
//        LifecycleProcessor lifecycleProcessor = lifecycleProcessor();
//        lifecycleProcessor.stop();
//        lifecycleProcessor.
        //lifecycleProcessorTest.start();
        // clientService.execute();
    }
}
