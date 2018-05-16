package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.article.ArticleRedisRepository;
import com.tim.vote.infrastructure.constant.IdKeyEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by luolibing on 2018/5/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VoteApplication.class)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRedisRepository articleRedisRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Test
    public void saveArticle() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(idGenerator.getNextId(IdKeyEnum.ARTICLE_ID.name()));
        articleEntity.setTitle("论母猪的产后护理");
        articleEntity.setTime(System.currentTimeMillis());
        articleEntity.setLink("http://www.baidu.com");
        articleEntity.setVotes(0L);
        articleEntity.setPoster("luolibing");
        articleRedisRepository.saveArticle(articleEntity, ArticleEntity.ArticleFileds.ALL_FIELD);
    }

    @Test
    public void findArticle() {
        ArticleEntity articleEntity = articleRedisRepository.findArticleEntity(14L, ArticleEntity.ArticleFileds.ALL_FIELD);
        System.out.println(articleEntity);
    }
}
