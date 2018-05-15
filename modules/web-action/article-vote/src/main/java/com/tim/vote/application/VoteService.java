package com.tim.vote.application;

import com.tim.vote.domain.constant.VoteOrderByEnum;
import com.tim.vote.domain.entity.ArticleEntity;
import com.tim.vote.infrastructure.article.ArticleRedisRepository;
import com.tim.vote.infrastructure.constant.RedisKeyEnum;
import com.tim.vote.infrastructure.redis.RedisSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * Created by luolibing on 2018/5/3.
 */
@Service
public class VoteService {

    @Autowired
    private RedisSupport redisSupport;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRedisRepository articleRedisRepository;

    private final static long ONE_TICKET_SCORE = 437;

    public void vote(long articleId, String userPin) {
        vote(articleId, userPin, VoteOrderByEnum.UP);
    }

    /**
     * 投票
     * @param articleId
     * @param userPin
     */
    public void vote(long articleId, String userPin, VoteOrderByEnum voteOrderBy) {
        // 验证， 这个人是否已经投过票，不能重复投票
        validate(articleId, userPin);

        // 投票记录流水表
        boolean result = redisSupport.putToSet(
                RedisKeyEnum.USER_ARTICLE_VOTED.name() + "_" + articleId, "user_" + userPin);
        if(!result) {
            throw new IllegalArgumentException("该同学已经投过票，不能重复投票");
        }

        long score;
        if(voteOrderBy == VoteOrderByEnum.UP) {
            score = ONE_TICKET_SCORE;
            // 票数更新
            articleRedisRepository.incrementVotes(articleId);
        } else {
            score = - ONE_TICKET_SCORE;
            articleRedisRepository.decrementVotes(articleId);
        }

        // 得分更新
        articleRedisRepository.incrementScore(articleId, score);
    }

    /**
     * 根据文章id查看投票人列表
     * @param articleId
     * @return
     */
    public Set<Object> findVotorsByArticleId(long articleId) {
        return redisSupport.members(RedisKeyEnum.USER_ARTICLE_VOTED + "_" + articleId);
    }

    private void validate(long articleId, String userPin) {
        ArticleEntity article = articleService.findArticle(articleId);
        Assert.notNull(article, "文章不存在" + articleId);
    }
}
