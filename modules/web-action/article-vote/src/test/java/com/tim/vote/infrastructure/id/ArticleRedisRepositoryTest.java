package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.article.ArticleRedisRepository;
import com.tim.vote.infrastructure.constant.IdKeyEnum;
import com.tim.vote.infrastructure.constant.RedisKeyEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by luolibing on 2018/5/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VoteApplication.class)
public class ArticleRedisRepositoryTest {

    @Autowired
    private ArticleRedisRepository articleRedisRepository;

    @Autowired
    private IdGenerator idGenerator;

    private Long articleId;

    @Before
    public void setup() {
        articleId = idGenerator.getNextId(IdKeyEnum.ARTICLE_ID.name());
    }

    @Test
    public void saveArticle() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(articleId);
        articleEntity.setLink("http://www.baidu.com");
        articleEntity.setPoster("zhangsan");
        articleEntity.setTime(System.currentTimeMillis());
        articleEntity.setTitle("论母猪的产后护理");

        articleRedisRepository.saveArticle(articleEntity, ArticleEntity.ArticleFileds.ALL_FIELD);
    }

    @Test
    public void findArticle() {
        long articleId = 13;
        ArticleEntity articleEntity = articleRedisRepository.findArticleEntity(articleId, ArticleEntity.ArticleFileds.ALL_FIELD);
        System.out.println(articleEntity);
    }

    @Test
    public void articleVote() {
        long articleId = 13;
        long votes = articleRedisRepository.incrementVotes(articleId);
        long expect = votes + 10;
        for(int i = 0; i < 10; i++) {
            votes = articleRedisRepository.incrementVotes(articleId);
        }

        assert expect == votes;
    }

    @Test
    public void findPage() {
        Pageable pageable = new PageRequest(0, 10);
        Page<ArticleEntity> page = articleRedisRepository.findArticlePage(RedisKeyEnum.ARTICLE_SCORE.name(), pageable);
        System.out.println(page);
    }
}
