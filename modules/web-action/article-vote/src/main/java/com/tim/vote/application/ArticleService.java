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

        articleRedisRepository.saveArticle(articleEntity, ArticleEntity.ArticleFileds.ALL_FIELD);
    }

    public ArticleEntity findArticle(long articleId) {
        return articleRedisRepository.findArticleEntity(articleId, ArticleEntity.ArticleFileds.ALL_FIELD);
    }

    public void delArticle(long articleId) {
        articleRedisRepository.delArticle(articleId);
    }
}
