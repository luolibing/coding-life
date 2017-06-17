package cn.tim.async;

import cn.tim.async.entity.User;
import cn.tim.async.service.GitLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Future;

/**
 * Created by LuoLiBing on 15/10/26.
 * 异步功能
 */
@SpringBootApplication
@EnableAsync
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    GitLookupService gitLookupService;


    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();
        // 异步Future
        Future<User> user1 = gitLookupService.findUser("luolibing");
        Future<User> user2 = gitLookupService.findUser("jiangtengfei");
        Future<User> user3 = gitLookupService.findUser("lizhengxing");
        // 阻塞等待处理完毕再进行下一个操作
        while (!user1.isDone() && !user2.isDone() && !user3.isDone()) {
            Thread.sleep(10);
        }

        System.out.println(user1.get());
        System.out.println(user2.get());
        System.out.println(user3.get());
        System.out.println("time:" + (System.currentTimeMillis() - start));
    }
}
