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
        ArticleEntity preArticle = redisSupport.saveEntity(
                idGenerator.generateKey(RedisKeyEnum.ARTICLE_KEY.name(), articleId.toString()), articleEntity);
        if(preArticle != null) {
            throw new IllegalArgumentException(articleId + " article is exists");
        }
    }

    public ArticleEntity findArticle(long articleId) {
        String articleKey = idGenerator.generateKey(RedisKeyEnum.ARTICLE_KEY.name(),
                Long.toString(articleId));
        return redisSupport.getEntity(articleKey, ArticleEntity.class);
    }
}
