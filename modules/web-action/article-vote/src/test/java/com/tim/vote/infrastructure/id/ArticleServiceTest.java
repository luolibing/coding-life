package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import com.tim.vote.application.ArticleService;
import com.tim.vote.application.RankService;
import com.tim.vote.application.VoteService;
import com.tim.vote.domain.constant.VoteOrderByEnum;
import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.constant.IdKeyEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;
import java.util.Random;

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

    @Autowired
    private RankService rankService;

    @Before
    public void setup() {
//        articleEntity = articleService.findArticle(2L);
    }

    @Test
    public void postArticle() {
        for(int i = 0; i < 100; i++) {
            articleEntity = new ArticleEntity();
            articleEntity.setId(idGenerator.getNextId(IdKeyEnum.ARTICLE_ID.name()));
            articleEntity.setLink("http://www.baidu.com");
            articleEntity.setPoster("zhangsan" + i);
            articleEntity.setTime(System.currentTimeMillis());
            articleEntity.setTitle("论母猪的产后护理" + i);
            articleService.postArticle(articleEntity);
        }
    }

    @Test
    public void findArticle() {
        ArticleEntity article = articleService.findArticle(4L);
        assert Objects.equals(article, articleEntity);
    }

    @Test
    public void vote() {
        Pageable pageable = new PageRequest(0, 100);
        Page<ArticleEntity> articlePage = rankService.findArticle(pageable);
        Random rand = new Random();
        articlePage.getContent().forEach(article -> {
            if(rand.nextInt(10) % 2 == 0) {
                voteService.vote(article.getId(), "wangwufhhaaa" + article.getId());
            }
        });
    }

    @Test
    public void voteUp() {
        voteService.vote(200L, "wangwu", VoteOrderByEnum.UP);
    }

    @Test
    public void voteDown() {
        voteService.vote(4L, "wangwu", VoteOrderByEnum.DOWN);
    }

    @Test
    public void page() {
        Pageable pageable = new PageRequest(0, 200);
        Page<ArticleEntity> articlePage = rankService.findArticle(pageable);
        articlePage.getContent().forEach(System.out::println);
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
