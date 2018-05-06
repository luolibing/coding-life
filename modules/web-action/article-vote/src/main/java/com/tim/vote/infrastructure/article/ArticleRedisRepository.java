package com.tim.vote.infrastructure.article;

import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.key.KeyGenerator;
import com.tim.vote.infrastructure.redis.RedisSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * articleRepository
 * Created by luolibing on 2018/5/5.
 */
@Repository
public class ArticleRedisRepository {

    @Autowired
    private RedisSupport redisSupport;

    public void saveArticle(ArticleEntity articleEntity, Set<ArticleEntity.ArticleFileds> saveFields) {
        String articleIdKey = KeyGenerator.articleIdKey(articleEntity.getId());
//        redisTemplate.multi();
        saveFields.forEach(field -> {
            Object value = getFieldValue(articleEntity, field.name());
            if(value != null) {
                redisSupport.putToHash(articleIdKey, field.name(), value);
            }
        });
//        redisTemplate.exec();
    }

    public ArticleEntity findArticleEntity(long articleId, Set<ArticleEntity.ArticleFileds> fields) {
        String articleIdKey = KeyGenerator.articleIdKey(articleId);
        ArticleEntity articleEntity = new ArticleEntity();
        fields.forEach(field -> {
            Object fieldValue = redisSupport.getFromHash(articleIdKey, field.name());
            if(fieldValue != null) {
                setFieldValue(articleEntity, field.name(), fieldValue);
            }
        });
        return articleEntity;
    }

    private Object getFieldValue(ArticleEntity articleEntity, String fieldName) {
        try {
            Method getMethod = BeanUtils.findDeclaredMethod(articleEntity.getClass(),
                    "get" + Character.toString(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1));
            return getMethod.invoke(articleEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setFieldValue(ArticleEntity articleEntity, String fieldName, Object value) {
        try {
            Method setterMethod = BeanUtils.findMethod(articleEntity.getClass(),
                    "set" + Character.toString(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1), value.getClass());
            setterMethod.invoke(articleEntity, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long incrementVotes(long articleId) {
        String articleIdKey = KeyGenerator.articleIdKey(articleId);
        return redisSupport.incrementHash(articleIdKey, ArticleEntity.ArticleFileds.votes, 1);
    }

    public void delArticle(long articleId) {
        redisSupport.deleteEntity(KeyGenerator.articleIdKey(articleId));
    }
}
