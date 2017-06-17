package cn.tim.async.service;

import cn.tim.async.entity.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Created by LuoLiBing on 15/10/26.
 * 使用restTemplate取代httpClient
 */
@Service
public class GitLookupService {

    RestTemplate restTemplate = new RestTemplate();


    @Async
    public Future<User> findUser(String username) throws InterruptedException {
        System.out.println("looking for " + username);
        User user = restTemplate.getForObject("https://api.github.com/users/" + username, User.class);
        Thread.sleep(1000L);
        return new AsyncResult<>(user);
    }
}
