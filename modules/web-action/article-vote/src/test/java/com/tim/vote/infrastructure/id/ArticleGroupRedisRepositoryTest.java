package com.tim.vote.infrastructure.id;

import com.tim.vote.VoteApplication;
import com.tim.vote.domain.entity.GroupEntity;
import com.tim.vote.infrastructure.article.ArticleGroupRedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by luolibing on 2018/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VoteApplication.class)
public class ArticleGroupRedisRepositoryTest {

    @Autowired
    private ArticleGroupRedisRepository articleGroupRedisRepository;

    @Test
    public void addGroup() {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName("java");
        articleGroupRedisRepository.saveGroup(groupEntity);
    }

    @Test
    public void addArticle() {
        articleGroupRedisRepository.addArticle(1L, 100L);
    }

    @Test
    public void addArticleScore() {
        articleGroupRedisRepository.addArticleScoreSet(1L, 100L, 2000);
    }
}
