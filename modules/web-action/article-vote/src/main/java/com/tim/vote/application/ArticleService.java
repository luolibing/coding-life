package com.tim.vote.application;

import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.constant.IdKeyEnum;
import com.tim.vote.infrastructure.constant.RedisKeyEnum;
import com.tim.vote.infrastructure.id.IdGenerator;
import com.tim.vote.infrastructure.redis.RedisSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luolibing on 2018/4/25.
 */
@Service
public class ArticleService {

    @Autowired
    private RedisSupport redisSupport;

    @Autowired
    private IdGenerator idGenerator;

    public void postArticle(ArticleEntity articleEntity) {
        Long articleId = idGenerator.getNextId(IdKeyEnum.ARTICLE_ID.name());
        articleEntity.setId(articleId);
        articleEntity.setTime(System.currentTimeMillis());
        articleEntity.setVotes(0);
        redisSupport.saveEntity(RedisKeyEnum.ARTICLE_KEY.name(), articleId.toString(), articleEntity);
    }

    public ArticleEntity findArticle(long articleId) {
        String articleKey = idGenerator.generateKey(IdKeyEnum.ARTICLE_ID.name(),
                Long.toString(articleId));
        return (ArticleEntity) redisSupport.getEntity(RedisKeyEnum.ARTICLE_KEY.name(), articleKey);
    }

    public void delArticle(long articleId) {
        String articleKey = idGenerator.generateKey(IdKeyEnum.ARTICLE_ID.name(),
                Long.toString(articleId));
        redisSupport.deleteEntity(articleKey);
    }
}
