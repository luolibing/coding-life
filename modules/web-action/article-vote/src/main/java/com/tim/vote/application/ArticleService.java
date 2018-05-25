package com.tim.vote.application;

import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.article.ArticleRedisRepository;
import com.tim.vote.infrastructure.constant.IdKeyEnum;
import com.tim.vote.infrastructure.id.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2018/4/25.
 */
@Service
public class ArticleService {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ArticleRedisRepository articleRedisRepository;

    public void postArticle(ArticleEntity articleEntity) {
        Long articleId = idGenerator.getNextId(IdKeyEnum.ARTICLE_ID.name());
        articleEntity.setId(articleId);
        articleEntity.setTime(System.currentTimeMillis());
        articleEntity.setVotes(0L);

        // 保存文章
        articleRedisRepository.saveArticle(articleEntity, ArticleEntity.ArticleFileds.ALL_INIT_FIELD);
        // 票数加1
        articleRedisRepository.incrementVotes(1);
        // 记录投票人
        articleRedisRepository.addUserVote(articleId, articleEntity.getPoster());
        // 分数新增
        articleRedisRepository.incrementScore(articleId, System.currentTimeMillis());
    }

    public ArticleEntity findArticle(long articleId) {
        return articleRedisRepository.findArticleEntity(articleId, ArticleEntity.ArticleFileds.ALL_FIELD);
    }

    public void delArticle(long articleId) {
        articleRedisRepository.delArticle(articleId);
    }
}
