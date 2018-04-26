package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.redis.RedisSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

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
        ArticleEntity preArticleEntity = redisSupport.saveEntity(key, articleEntity);
        assert preArticleEntity == null;
        ArticleEntity entity = redisSupport.getEntity(key, ArticleEntity.class);
        assert Objects.equals(entity, articleEntity);
    }

    @After
    public void clear() {
        redisSupport.deleteEntity(key);
    }
}
