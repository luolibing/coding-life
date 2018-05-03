package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.constant.RedisKeyEnum;
import com.tim.vote.infrastructure.redis.RedisSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by luolibing on 2018/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VoteApplication.class)
public class RedisSupportTest {

    @Autowired
    private RedisSupport redisSupport;

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
        redisSupport.putToSet(RedisKeyEnum.USER_ARTICLE_VOTED.name() + "_" + 100, "user_1001");
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
        redisSupport.increment(RedisKeyEnum.ARTICLE_SCORE.name(), "article_1001", 100 * 345);
    }

    @Test
    public void score() {
        double score = redisSupport.getScore(RedisKeyEnum.ARTICLE_SCORE.name(), "article_1001");
        System.out.println(score);
    }

    @After
    public void clear() {
        redisSupport.deleteEntity(key);
    }
}
