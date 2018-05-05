package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.constant.RedisKeyEnum;
import com.tim.vote.infrastructure.redis.RedisSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Created by luolibing on 2018/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VoteApplication.class)
public class RedisSupportTest {

    @Autowired
    private RedisSupport redisSupport;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String key = "article_test_1001";

    @Before
    public void setUp() {
        redisSupport.deleteEntity(key);
    }

    @Test
    public void put() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(100L);
        articleEntity.setLink("http://www.baidu.com");
        articleEntity.setPoster("zhangsan");
        articleEntity.setTime(System.currentTimeMillis());
        articleEntity.setTitle("论母猪的产后护理");
        articleEntity.setVotes(10000L);
        redisSupport.saveEntity(RedisKeyEnum.ARTICLE_KEY.name(), key, articleEntity);
    }

    @Test
    public void find() {
        ArticleEntity articleEntity = (ArticleEntity) redisSupport.getEntity(
                RedisKeyEnum.ARTICLE_KEY.name(), key);
        System.out.println(articleEntity);
    }

    @Test
    public void putToSet() {
        boolean result = redisSupport.putToSet(RedisKeyEnum.USER_ARTICLE_VOTED.name() + "_" + 100, "user_1001");
        System.out.println(result);
        result = redisSupport.putToSet(RedisKeyEnum.USER_ARTICLE_VOTED.name() + "_" + 100, "user_1001");
        System.out.println(result);
    }

    @Test
    public void isMember() {
        boolean user1001 = redisSupport.isInSet(RedisKeyEnum.USER_ARTICLE_VOTED.name() + "_" + 100, "user_1001");
        System.out.println(user1001);
    }

    @Test
    public void putToZSet() {
        redisSupport.putToZSet(RedisKeyEnum.ARTICLE_SCORE.name(), "article_1001", System.currentTimeMillis());
    }

    @Test
    public void increment() {
        Random rand = new Random();

        IntStream.range(0, 1000).forEach((i) -> {
            double score = redisSupport.increment(RedisKeyEnum.ARTICLE_SCORE.name(), "article_" + rand.nextInt(100), 1000);
            System.out.println(score);
        });

    }

    @Test
    public void score() {
        double score = redisSupport.getScore(RedisKeyEnum.ARTICLE_SCORE.name(), "article_1001");
        System.out.println(score);
    }

    @Test
    public void members() {
        Set<Object> members = redisSupport.members(RedisKeyEnum.USER_ARTICLE_VOTED.name() + "_" + 100);
        members.forEach(System.out::println);
    }

    @Test
    public void range() {
        Set<ZSetOperations.TypedTuple<Object>> range = redisSupport.range(RedisKeyEnum.ARTICLE_SCORE.name(), false, 0, 10);
        range.forEach(o -> System.out.println("articleId: " + o.getValue() + ", score: " + o.getScore()));
    }

    @Test
    public void multi() {
        redisTemplate.multi();
        redisTemplate.opsForHash().put("luolibing", "name", "llb");
        redisTemplate.opsForHash().put("luolibing", "age", 20);
        redisTemplate.exec();
    }

//    @After
//    public void clear() {
//        redisSupport.deleteEntity(key);
//    }
}
