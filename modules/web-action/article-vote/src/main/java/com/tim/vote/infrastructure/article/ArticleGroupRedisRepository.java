package com.tim.vote.infrastructure.article;

import com.tim.vote.domain.entity.GroupEntity;
import com.tim.vote.infrastructure.constant.IdKeyEnum;
import com.tim.vote.infrastructure.id.IdGenerator;
import com.tim.vote.infrastructure.key.KeyGenerator;
import com.tim.vote.infrastructure.redis.RedisSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by luolibing on 2018/5/18.
 */
@Repository
public class ArticleGroupRedisRepository {

    @Autowired
    private RedisSupport redisSupport;

    @Autowired
    private IdGenerator idGenerator;

    public void saveGroup(GroupEntity groupEntity) {
        Long groupId = idGenerator.getNextId(IdKeyEnum.GROUP_ID.name());
        String groupRedisId = KeyGenerator.groupId(groupId);
        redisSupport.putToHash(groupRedisId, "id", groupId);
        redisSupport.putToHash(groupRedisId, "name", groupEntity.getName());
    }

    public void addArticle(long groupId, long articleId) {
        String groupRedisId = KeyGenerator.groupId(groupId);
        String groupSetKey = "set_" + groupRedisId;
        String articleIdKey = KeyGenerator.articleIdKey(articleId);
        redisSupport.putToSet(groupSetKey, articleIdKey);
    }

    public void addArticleScoreSet(long groupId, long articleId, long score) {
        String groupRedisId = KeyGenerator.groupId(groupId);
        String groupSetKey = "score_" + groupRedisId;
        String articleIdKey = KeyGenerator.articleIdKey(articleId);
        redisSupport.putToZSet(groupSetKey, articleIdKey, score);
    }

    public void articleScoreIncrement(long groupId, long articleId, long addScore) {
        String groupRedisId = KeyGenerator.groupId(groupId);
        String groupSetKey = "score_" + groupRedisId;
        String articleIdKey = KeyGenerator.articleIdKey(articleId);
        redisSupport.increment(groupSetKey, articleIdKey, addScore);
    }

    public Page<GroupEntity.ArticleScore> findArticleScorePage(long groupId, Pageable pageable) {
        String groupRedisId = KeyGenerator.groupId(groupId);
        String groupSetKey = "score_" + groupRedisId;
        long count = redisSupport.findCountOfZset(groupSetKey);
        long offset = pageable.getOffset();
        long pageSize = pageable.getPageSize();
        Set<ZSetOperations.TypedTuple<Object>> scoreSet = redisSupport.range(
                groupSetKey, false, offset, pageSize);
        List<GroupEntity.ArticleScore> data = scoreSet.stream().map(
                t -> {
                    GroupEntity.ArticleScore articleScore = new GroupEntity.ArticleScore();
                    articleScore.setArticleId((String) t.getValue());
                    articleScore.setScore(t.getScore());
                    return articleScore;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(data, pageable, count);
    }
}
