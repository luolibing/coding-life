package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import com.tim.vote.application.ArticleService;
import com.tim.vote.application.VoteService;
import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.constant.IdKeyEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

/**
 * Created by luolibing on 2018/5/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VoteApplication.class)
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private IdGenerator idGenerator;

    private ArticleEntity articleEntity;

    @Autowired
    private VoteService voteService;

    @Before
    public void setup() {
//        articleEntity = articleService.findArticle(2L);
    }

    @Test
    public void postArticle() {
        articleEntity = new ArticleEntity();
        articleEntity.setId(idGenerator.getNextId(IdKeyEnum.ARTICLE_ID.name()));
        articleEntity.setLink("http://www.baidu.com");
        articleEntity.setPoster("zhangsan");
        articleEntity.setTime(System.currentTimeMillis());
        articleEntity.setTitle("论母猪的产后护理");
        articleService.postArticle(articleEntity);
    }

    @Test
    public void findArticle() {
        ArticleEntity article = articleService.findArticle(4L);
        assert Objects.equals(article, articleEntity);
    }

    @Test
    public void vote() {
        voteService.vote(4L, "zhangsan");
    }

    @Test
    public void delArticle() {
        articleService.delArticle(articleEntity.getId());
    }



    @After
    public void clearUp() {
//        delArticle();
    }
}
